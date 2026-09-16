package org.emat.service.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.annotation.PostConstruct;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Locale;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.emat.dto.CaptchaResponse;
import org.emat.service.CaptchaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Self-contained, backend-only CAPTCHA implementation.
 *
 * <p>Generation: a random code is rendered into a noisy PNG image (Java2D) and returned to the
 * client as a data URI along with a unique captcha id. The expected answer is stored in-memory (a
 * Caffeine cache) as a SHA-256 digest, keyed by the captcha id, so the plaintext answer never
 * reaches the client or memory for longer than the cache lives. Validation consumes the entry
 * atomically, making each CAPTCHA single-use and preventing replay of the same image/answer pair.
 *
 * <p>Note: the store is per-instance, so a multi-node deployment behind a load balancer must share
 * the CAPTCHA store (or stick the client to one node) for generation/validation to match.
 */
@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

    private static final String DATA_URI_PREFIX = "data:image/png;base64,";
    private static final String CODE_CHARSET =
            "ABCDEFGHJKMNPQRSTUVWXYZ23456789"; // excludes 0/O, 1/I/L

    private final SecureRandom random = new SecureRandom();

    @Value("${security.captcha.enabled:true}")
    private boolean enabled;

    @Value("${security.captcha.expiry-in-seconds:300}")
    private long expiryInSeconds;

    @Value("${security.captcha.length:5}")
    private int length;

    @Value("${security.captcha.image.width:180}")
    private int imageWidth;

    @Value("${security.captcha.image.height:60}")
    private int imageHeight;

    private Cache<String, String> captchaStore;

    @PostConstruct
    void init() {
        long expiry = Math.max(expiryInSeconds, 30L);
        captchaStore =
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofSeconds(expiry))
                        .maximumSize(10_000)
                        .build();
        int captchaLength = Math.min(Math.max(length, 4), 8);
        length = captchaLength;
        log.info(
                "CAPTCHA configured: enabled={}, length={}, expiry-in-seconds={}",
                enabled,
                length,
                expiry);
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public CaptchaResponse generate() {
        String code = randomCode();
        String captchaId = UUID.randomUUID().toString();
        captchaStore.put(captchaId, hashToHex(normalize(code)));
        byte[] png = renderPng(code);
        String dataUri = DATA_URI_PREFIX + Base64.getEncoder().encodeToString(png);
        return new CaptchaResponse(captchaId, dataUri, expiryInSeconds);
    }

    @Override
    public boolean consumeAndValidate(String captchaId, String answer) {
        if (captchaId == null || captchaId.isBlank() || answer == null || answer.isBlank()) {
            return false;
        }
        String stored = captchaStore.asMap().remove(captchaId);
        if (stored == null) {
            return false;
        }
        byte[] storedBytes = HexFormat.of().parseHex(stored);
        byte[] givenBytes = digest(normalize(answer));
        return MessageDigest.isEqual(storedBytes, givenBytes);
    }

    private String randomCode() {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CODE_CHARSET.charAt(random.nextInt(CODE_CHARSET.length())));
        }
        return sb.toString();
    }

    private String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private String hashToHex(String value) {
        return HexFormat.of().formatHex(digest(value));
    }

    private byte[] digest(String value) {
        try {
            return MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    private byte[] renderPng(String code) {
        BufferedImage image =
                new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g.setColor(randomLightColor());
            g.fillRect(0, 0, imageWidth, imageHeight);

            g.setStroke(new BasicStroke(1.4f));
            for (int i = 0; i < 7; i++) {
                g.setColor(randomTranslucentColor());
                int x1 = random.nextInt(imageWidth);
                int y1 = random.nextInt(imageHeight);
                int x2 = random.nextInt(imageWidth);
                int y2 = random.nextInt(imageHeight);
                g.drawLine(x1, y1, x2, y2);
            }

            Font baseFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
            int step = imageWidth / (code.length() + 1);
            int startX = step / 2;
            for (int i = 0; i < code.length(); i++) {
                char c = code.charAt(i);
                Font font = baseFont.deriveFont((float) (26 + random.nextInt(8)));
                g.setFont(font);
                FontMetrics metrics = g.getFontMetrics(font);
                int cw = metrics.charWidth(c);
                int cx = startX + i * step + random.nextInt(step / 4);
                int baseline =
                        (imageHeight + metrics.getAscent() - metrics.getDescent()) / 2
                                + random.nextInt(6)
                                - 3;
                double theta = (random.nextDouble() - 0.5) * 0.7;
                g.setColor(randomDarkColor());
                AffineTransform old = g.getTransform();
                g.rotate(theta, cx, baseline);
                g.drawString(String.valueOf(c), cx - cw / 2, baseline);
                g.setTransform(old);
            }

            g.setColor(randomTranslucentColor());
            for (int i = 0; i < 90; i++) {
                int x = random.nextInt(imageWidth);
                int y = random.nextInt(imageHeight);
                int s = 1 + random.nextInt(3);
                g.fillRect(x, y, s, s);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to render CAPTCHA image", e);
        } finally {
            g.dispose();
        }
    }

    private Color randomLightColor() {
        return Color.getHSBColor(
                random.nextFloat(),
                0.10f + random.nextFloat() * 0.30f,
                0.85f + random.nextFloat() * 0.15f);
    }

    private Color randomDarkColor() {
        return Color.getHSBColor(
                random.nextFloat(),
                0.55f + random.nextFloat() * 0.45f,
                0.25f + random.nextFloat() * 0.40f);
    }

    private Color randomTranslucentColor() {
        int rgb = (random.nextInt(0x1000000)) | (0x40 + random.nextInt(0x60)) << 24;
        return new Color(rgb, true);
    }
}
