package com.delas.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    private final WebClient webClient;

    @Value("${BREVO_API_KEY}")
    private String apiKey;

    @Value("${FROM_EMAIL}")
    private String fromEmail;

    @Value("${FROM_NAME:+Delas}")
    private String fromName;

    public EmailService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.brevo.com").build();
    }

    public void sendRecoveryEmail(String toEmail, String resetLink) {
        Map<String, Object> payload = Map.of(
                "sender", Map.of("email", fromEmail, "name", fromName),
                "to", List.of(Map.of("email", toEmail)),
                "subject", "Link de Recuperação de Senha",
                "textContent", "Clique no link abaixo para redefinir sua senha:\n\n" + resetLink);

        webClient.post()
                .uri("/v3/smtp/email")
                .header("api-key", apiKey)
                .bodyValue(payload)
                .retrieve()
                .onStatus(s -> s.isError(), r -> r.bodyToMono(String.class).map(RuntimeException::new))
                .toBodilessEntity()
                .block();

    }
}
