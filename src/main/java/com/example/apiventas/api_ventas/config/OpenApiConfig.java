
package com.example.apiventas.api_ventas.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        final String securitySchemeName = "bearerAuth";


        Server server = new Server();
        server.setUrl("http://localhost:8080");
        
        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("API Ventas")
                        .description("API REST para gestión de ventas e inventario")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Tu Nombre/Empresa")
                                .email("contacto@ejemplo.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                // Definir el esquema de seguridad para JWT Bearer
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ))
                // Aplicar la seguridad globalmente a todos los endpoints
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}