package com.delas.api.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Verifica se o token está presente no cabeçalho Authorization
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                // A chave secreta para validação do token
                String SECRET_KEY = "your_very_secret_and_long_key_that_is_256_bits_long";
                SecretKey key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key) // Use o mesmo formato de chave usada na geração
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                username = claims.getSubject();
            } catch (Exception e) {
                System.out.println("Erro ao validar o token: " + e.getMessage());
            }
        }

        // Se o username foi extraído e o contexto de segurança ainda não tem um usuário autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Criação do objeto de autenticação
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList()); // Adicionar authorities se necessário
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // Define a autenticação no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Chama o próximo filtro na cadeia de filtros
        chain.doFilter(request, response);
    }
}
