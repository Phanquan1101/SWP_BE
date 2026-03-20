package com.crowdsourced.wasteplatform.service.email;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private static final String PROVIDER_RESEND = "RESEND";
    private static final String PROVIDER_BREVO = "BREVO";

    @Qualifier("emailRestClient")
    private final RestClient restClient;

    @Value("${app.email.provider:RESEND}")
    private String provider;

    @Value("${app.email.api-key:}")
    private String apiKey;

    @Value("${app.email.from:no-reply@example.com}")
    private String fromEmail;

    @Value("${app.email.from-name:Waste Platform}")
    private String fromName;

    @Value("${app.email.resend-url:https://api.resend.com/emails}")
    private String resendUrl;

    @Value("${app.email.brevo-url:https://api.brevo.com/v3/smtp/email}")
    private String brevoUrl;

    @Async("mailTaskExecutor")
    public void sendComplaintResolvedEmail(String toEmail,
                                           String subject,
                                           String text) {
        if (toEmail == null || toEmail.isBlank()) {
            log.warn("Skip email: recipient is empty");
            return;
        }
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("Skip email to {} because app.email.api-key is empty", toEmail);
            return;
        }

        try {
            String resolvedProvider = provider == null ? PROVIDER_RESEND : provider.trim().toUpperCase(Locale.ROOT);
            if (PROVIDER_BREVO.equals(resolvedProvider)) {
                sendViaBrevo(toEmail, subject, text);
            } else {
                sendViaResend(toEmail, subject, text);
            }
        } catch (RestClientResponseException ex) {
            log.warn("Email API error to {}: status={}, body={}",
                toEmail, ex.getStatusCode(), ex.getResponseBodyAsString());
        } catch (Exception ex) {
            log.warn("Email sending failed to {}: {}", toEmail, ex.getMessage());
        }
    }

    private void sendViaResend(String toEmail, String subject, String text) {
        Map<String, Object> payload = Map.of(
            "from", fromName + " <" + fromEmail + ">",
            "to", List.of(toEmail),
            "subject", subject,
            "text", text
        );

        restClient.post()
            .uri(resendUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + apiKey)
            .body(payload)
            .retrieve()
            .toBodilessEntity();
    }

    private void sendViaBrevo(String toEmail, String subject, String text) {
        Map<String, Object> payload = Map.of(
            "sender", Map.of(
                "name", fromName,
                "email", fromEmail
            ),
            "to", List.of(
                Map.of("email", toEmail)
            ),
            "subject", subject,
            "textContent", text
        );

        restClient.post()
            .uri(brevoUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .header("api-key", apiKey)
            .body(payload)
            .retrieve()
            .toBodilessEntity();
    }
}

