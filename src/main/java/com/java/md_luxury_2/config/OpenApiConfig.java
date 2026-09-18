package com.java.md_luxury_2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MD Luxury 2 API Document")
                        .version("1.0.0")
                        .description("Hệ thống RESTful API tích hợp RabbitMQ và PostgreSQL cho MD Luxury 2")
                        .contact(new Contact()
                                .name("MD Luxury Team")
                                .email("admin@mdluxury.vn"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}