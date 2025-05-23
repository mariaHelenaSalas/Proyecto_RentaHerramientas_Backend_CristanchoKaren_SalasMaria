package com.proyecto.proyecto_renta.infrastructure.Swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tool Rental API")
                        .description("API for a Tool and Construction Equipment Rental Platform")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Karen Lorena Cristancho Caceres")
                                .email("karenlorenacriscaceres@gmail.com")
                                .url("https://github.com/mariaHelenaSalas/Proyecto_RentaHerramientas_Backend_CristanchoKaren_SalasMaria.git.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://127.0.0.1:5500/Pages/index.html")))
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
}
