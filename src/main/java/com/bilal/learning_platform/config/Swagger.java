package com.bilal.learning_platform.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class Swagger extends WebMvcConfigurerAdapter {

    @Autowired
    private TypeResolver typeResolver;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @PostConstruct
    public void postConstruct() {

    }

    @Bean
    public Docket consumerSwaggerConfiguration() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.select()
                .apis(RequestHandlerSelectors.basePackage("com.bilal.learning_platform.controller"))
                .build().groupName("Consumer")
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(Time.class, String.class)
                .directModelSubstitute(Timestamp.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class,
                                typeResolver.resolve(ResponseEntity.class)),
                                typeResolver.resolve(WildcardType.class)))
                .apiInfo(getApiInfo())
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
                .enableUrlTemplating(false);

        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey(
                "Authorization",
                "Authorization",
                "header");
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Learning Platform Backend",
                "com.bilal.learning_platform.controller",
                "v1.0",
                "in-app-purchase",
                new Contact("", "", "ahamedbilalazmy@gmail.com"),
                "MIT Licences",
                "",
                Collections.emptyList());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
}
