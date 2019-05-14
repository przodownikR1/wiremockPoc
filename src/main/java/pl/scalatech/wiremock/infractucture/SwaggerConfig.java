package pl.scalatech.wiremock.infractucture;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.OffsetDateTime;
import java.util.Date;

import static java.util.Collections.singletonList;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
@RequiredArgsConstructor
class SwaggerConfig implements WebMvcConfigurer {

    private static final String WEBJARS = "/webjars/**";
    private static final String SWAGGER_UI_HTML = "swagger-ui.html";
    private static final String PACKAGE = "pl.scalatech.wiremock.adapter.web";
    public static final String API_KEY_NAME = "Authorization-Key";

    @Bean
    Docket api() {
        return new Docket(SWAGGER_2)
                .securitySchemes(singletonList(createApiKey(API_KEY_NAME)))
                .securityContexts(singletonList(createSecurityContext(API_KEY_NAME)))
                .groupName("wiremockPoc")
                .select()
                .apis(RequestHandlerSelectors.basePackage(PACKAGE))
                .apis(RequestHandlerSelectors.any())
                .paths(any())
                .build()
                .enable(true)
                .apiInfo(apiInfo())
                .directModelSubstitute(OffsetDateTime.class, Date.class)
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(WebRequest.class);
    }

    @Bean
    public Docket actuatorApi() {
        return new Docket(SWAGGER_2)
                .groupName("Spring Actuator")
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.springframework.boot.actuate"))
                .paths(regex("/actuator/.*"))
                .build();
    }

    private ApiKey createApiKey(String apiKeyName) {
        return new ApiKey(apiKeyName, HttpHeaders.AUTHORIZATION, "header");
    }

    private SecurityContext createSecurityContext(String apiKeyName) {
        AuthorizationScope authorizationScope = new AuthorizationScopeBuilder()
                .scope("global")
                .description("full access")
                .build();
        SecurityReference securityReference = SecurityReference.builder()
                                                               .reference(apiKeyName)
                                                               .scopes(new AuthorizationScope[]{authorizationScope})
                                                               .build();
        return SecurityContext.builder()
                              .securityReferences(singletonList(securityReference))
                              .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Wiremock API")
                .description(
                        "A service that provides Wiremock functionality\n\n"
                                + "Build Informations:"
                                + "\n\nApplication: ")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler(SWAGGER_UI_HTML)
                .addResourceLocations("classpath:/META-INF/resources/");
        registry
                .addResourceHandler(WEBJARS)
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
