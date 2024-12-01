package com.delas.api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component  // Adiciona a anotação @Component para registrar como bean do Spring
public class JwtUtil {

    private static final String SECRET_KEY = "your_very_secret_and_long_key_that_is_256_bits_long!!"; // Exemplo de chave longa com 256 bits


    // Método para gerar o token JWT
    public static String generateToken(String email) {
        SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Token expira em 1 hora
                .signWith(key)  // Usando a chave secreta como SecretKey
                .compact();
    }

    // Método para extrair o e-mail do token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())  // Definir chave correta para validar o token
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Método para validar o token
    public boolean validateToken(String token, String email) {
        String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(email) && !isTokenExpired(token));
    }

    // Método para verificar se o token expirou
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())  // Definir chave correta para validar o token
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}
