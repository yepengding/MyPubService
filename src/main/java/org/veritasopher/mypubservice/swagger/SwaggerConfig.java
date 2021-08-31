package org.veritasopher.mypubservice.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("org.veritasopher.mypubservice.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponses(HttpMethod.GET, newArrayList(
                        new ResponseBuilder().code("500")
                                .description("Internal Error").build(),
                        new ResponseBuilder().code("403")
                                .description("Forbidden").build()
                ));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "MyPub Service API Document",
                "Description of MyPub Service API",
                "V1.0",
                null,
                new Contact("Yepeng Ding", "https://yepengding.github.io/", "yepengding@g.ecc.u-tokyo.ac.jp"),
                "MIT License",
                "https://github.com/yepengding/IPFSServerAPISimulator/blob/main/LICENSE",
                Collections.emptyList());
        return apiInfo;
    }
}