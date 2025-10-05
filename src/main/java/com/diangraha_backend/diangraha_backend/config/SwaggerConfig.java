package com.diangraha_backend.diangraha_backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Diangraha Backend API")
                        .version("1.0.0")
                        .description("API Documentation for Diangraha Backend Application")
                        .contact(new Contact()
                                .name("Diangraha Team")
                                .email("support@diangraha.com")))
                .servers(List.of(
                        new Server()
                                .url("http://103.103.20.23:8080")
                                .description("Production Server"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Server"),
                        new Server()
                                .url("/")
                                .description("Current Server")
                ));
    }
}