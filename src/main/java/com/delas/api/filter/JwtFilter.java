package com.delas.api.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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

@Component // Declara a classe como um componente gerenciado pelo Spring
public class JwtFilter extends OncePerRequestFilter {

    // Chave secreta usada para assinar e validar tokens JWT
    private final String SECRET_KEY = "sua_chave_secreta_super_segura";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        // Obtém o cabeçalho Authorization da requisição
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null; // Nome de usuário extraído do token
        String jwt = null; // Token JWT

        // Verifica se o cabeçalho Authorization contém um token JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Remove o prefixo "Bearer " do token
            jwt = authorizationHeader.substring(7);

            try {
                // Extrai as claims (informações) do token JWT
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY) // Configura a chave secreta para validação
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                // Obtém o nome de usuário (subject) do token
                username = claims.getSubject();
            } catch (Exception e) {
                // Caso ocorra um erro durante a validação do token (ex.: token inválido ou expirado)
                System.out.println("Token inválido: " + e.getMessage());
            }
        }

        // Verifica se o nome de usuário foi extraído e se o usuário ainda não está autenticado
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cria o token de autenticação do Spring Security
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, null);

            // Adiciona detalhes da requisição (como IP, user-agent) ao token de autenticação
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            // Define o usuário autenticado no contexto de segurança do Spring
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // Continua o processamento da requisição para outros filtros ou controladores
        chain.doFilter(request, response);
    }
}
