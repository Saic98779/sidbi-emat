package org.emat.config;

import org.emat.util.PiiEncryptionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Converts encrypted {@code Long} identifiers received in REST URL path segments
 * ({@code @PathVariable}) or query strings ({@code @RequestParam}) into their plain backend values.
 * Strict mode: plain numeric values are rejected with an {@link IllegalArgumentException} -
 * identifiers must always travel encrypted.
 */
@Component
public class EncryptedIdConverter implements Converter<String, Long> {

    private final PiiEncryptionService encryptionService;

    public EncryptedIdConverter(PiiEncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    @Nullable
    public Long convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        source = source.replaceAll("^\"|\"$", "");
        return encryptionService.decryptId(source);
    }
}
