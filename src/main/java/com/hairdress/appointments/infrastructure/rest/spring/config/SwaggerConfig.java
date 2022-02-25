package com.hairdress.appointments.infrastructure.rest.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ApiInfo apiInfo = new ApiInfo("Documentación de Citas",
            "Listado con los endpoints REST de Citas"
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator())),
            "1.0.0", "", null, "", "", Collections.emptyList());

    @Bean
    public Docket docketConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .directModelSubstitute(java.sql.Timestamp.class, java.sql.Timestamp.class)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.hairdress.appointments.infrastructure.rest.spring.controller"))
                .build()
                .apiInfo(apiInfo);
    }


}
