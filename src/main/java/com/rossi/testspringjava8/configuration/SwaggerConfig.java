package com.rossi.testspringjava8.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(){
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name("Accept-Language")
                .description("Accept Language")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .defaultValue("en")
                .build();

        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.doku.middlemart.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(parameters)
                ;
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "DOKU WALLET MIDDLE MART DOCUMENTATION",
                "API for Middle Market Partner",
                "1.0",
                "Terms of Service",
                new Contact("DW Backend Team", "", ""),
                "License of API", "API license URL", new ArrayList<VendorExtension>());

    }
}
