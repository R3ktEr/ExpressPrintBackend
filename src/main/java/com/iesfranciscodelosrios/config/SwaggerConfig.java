package com.iesfranciscodelosrios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("ExpressPrint").select().apis(RequestHandlerSelectors.basePackage("com.iesfranciscodelosrios.controller")).paths(PathSelectors.any()).build().apiInfo(getApiInfo());
    }

    @Bean
    public ApiInfo getApiInfo() {
        return new ApiInfo("ExpressPrint Api", "RestFul Api developed in SpringBoot", "1.0.0", "https://github.com/R3ktEr/ExpressPrintBackend", new Contact("Cristian", "https://github.com/R3ktEr/", "a@a"), "License", "https://github.com/R3ktEr/ExpressPrintBackend/", Collections.emptyList());
    }
}

