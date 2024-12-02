package com.delas.api.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")  // O endereço de e-mail do remetente
    private String senderEmail;

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Método para enviar o e-mail de recuperação de senha
    public void sendRecoveryEmail(String toEmail, String resetLink) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Configuração do e-mail
        helper.setFrom(senderEmail);  // De: o e-mail configurado no application.properties
        helper.setTo(toEmail);  // Para: o e-mail do destinatário
        helper.setSubject("Link de Recuperação de Senha");  // Assunto
        helper.setText("Clique no link abaixo para redefinir sua senha:\n\n" + resetLink);  // Corpo do e-mail

        // Envia o e-mail
        mailSender.send(message);
    }
}
