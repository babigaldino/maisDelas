package com.delas.api.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "sua_chave_secreta_super_segura"; // A chave secreta para validação do token

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
                // Parse do JWT para extrair as claims
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                username = claims.getSubject(); // Obtem o nome de usuário
            } catch (SignatureException e) {
                // Caso o token seja inválido
                System.out.println("Token inválido: " + e.getMessage());
            } catch (Exception e) {
                // Captura de outras exceções
                System.out.println("Erro ao processar o token: " + e.getMessage());
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
