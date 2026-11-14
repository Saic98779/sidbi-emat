package org.emat.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.emat.event.UserCreatedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Sends a welcome email to a newly registered user.
 *
 * <p>Runs only after the user's transaction commits (so no email goes out for a registration that
 * rolled back) and on a background thread (so SMTP latency doesn't slow down the API response).
 * A mail failure is logged and never affects the registration itself.
 */
@Component
@Slf4j
public class WelcomeEmailListener {

    private final JavaMailSender mailSender;

    @Value("${security.welcome-mail.enabled:true}")
    private boolean enabled;

    @Value("${security.welcome-mail.subject:Welcome to EMAT}")
    private String subject;

    @Value("${security.welcome-mail.login-url:}")
    private String loginUrl;

    @Value("${security.password-reset.mail-from:}")
    private String mailFrom;

    public WelcomeEmailListener(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    public void onUserCreated(UserCreatedEvent event) {
        if (!enabled) {
            return;
        }
        if (event.email() == null || event.email().isBlank()) {
            log.info("No email for new user id {}; skipping welcome email", event.userId());
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        if (mailFrom != null && !mailFrom.isBlank()) {
            message.setFrom(mailFrom);
        }
        message.setTo(event.email().trim());
        message.setSubject(subject);
        message.setText(buildBody(event));
        try {
            mailSender.send(message);
            log.info("Welcome email sent to user id {}", event.userId());
        } catch (MailException ex) {
            log.error("Failed to send welcome email to user id {}", event.userId(), ex);
        }
    }

    private String buildBody(UserCreatedEvent event) {
        String name = displayName(event);
        StringBuilder body = new StringBuilder();
        body.append("Dear ").append(name).append(",\n\n")
                .append("Welcome to EMAT! Your account has been created successfully.\n\n")
                .append("Username: ").append(event.username()).append("\n");
        if (loginUrl != null && !loginUrl.isBlank()) {
            body.append("Login: ").append(loginUrl.trim()).append("\n");
        }
        body.append("\nYou can log in with your username and the password set during")
                .append(" registration. If you forget it, use \"Forgot password\" on the login")
                .append(" page.\n\n")
                .append("If you did not expect this account, please contact the administrator.\n\n")
                .append("Regards,\nEMAT Team");
        return body.toString();
    }

    private static String displayName(UserCreatedEvent event) {
        String first = event.firstName() == null ? "" : event.firstName().trim();
        String last = event.lastName() == null ? "" : event.lastName().trim();
        String full = (first + " " + last).trim();
        return full.isEmpty() ? event.username() : full;
    }
}
