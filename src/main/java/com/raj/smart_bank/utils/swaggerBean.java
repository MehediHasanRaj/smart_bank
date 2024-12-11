package com.raj.smart_bank.utils;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class swaggerBean {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart Bank API Documentation")
                        .version("1.0.0")
                        .description("Detailed API documentation for our application with High Security.")
                        .contact(new Contact()
                                .name("Md Mehedi Hasan Raj")
                                .email("mehedihasanraj007@gmail.com")
                                .url("https:mehedi-hasan-raj.co.uk"))
                        )
                ;
    }
}
