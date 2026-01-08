package com.delas.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // ✅ ORIGENS PERMITIDAS (desenvolvimento + produção)
        configuration.addAllowedOrigin("http://localhost:3000");      // Vite default
        configuration.addAllowedOrigin("http://localhost:5173");      // Vite alt
        configuration.addAllowedOrigin("http://127.0.0.1:5173");      // localhost alt
        configuration.addAllowedOrigin("http://localhost:8080");      // Backend local
        // configuration.addAllowedOrigin("https://seu-dominio.com"); // Produção (descomente)
        
        // ✅ MÉTODOS HTTP
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("OPTIONS");
        
        // ✅ HEADERS PERMITIDOS (crítico para JWT!)
        configuration.addAllowedHeader("Authorization");  // JWT no header
        configuration.addAllowedHeader("Content-Type");   // application/json
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedHeader("X-Requested-With");
        configuration.addAllowedHeader("*");              // Aceita qualquer header
        
        // ✅ HEADERS EXPOSTOS (frontend consegue ler)
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("Content-Type");
        
        // ✅ CREDENCIAIS (importante para cookies + JWT)
        configuration.setAllowCredentials(true);
        
        // ✅ CACHE DO PREFLIGHT (1 hora = 3600 segundos)
        configuration.setMaxAge(3600L);
        
        // ✅ APLICAR EM TODOS OS ENDPOINTS
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
