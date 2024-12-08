package com.delas.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define o esquema de segurança JWT
        SecurityScheme securityScheme = new SecurityScheme()
                .name("BearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        // Cria o componente com o esquema de segurança
        Components components = new Components()
                .addSecuritySchemes("BearerAuth", securityScheme);

        // Define o requerimento de segurança global
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("BearerAuth");

        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("API Mais Delas")
                        .version("1.0.0")
                        .description("Documentação da API Mais Delas com suporte a autenticação JWT"));
    }
}
