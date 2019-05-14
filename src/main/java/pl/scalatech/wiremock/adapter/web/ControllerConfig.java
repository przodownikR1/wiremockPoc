package pl.scalatech.wiremock.adapter.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.MappedInterceptor;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Configuration
class ControllerConfig {

    @Bean
    ApiKeyInterceptor apiKeyInterceptor(@Value("${security.apiKey}") String apiKey) {
        return ApiKeyInterceptor.builder()
                                .headerName(AUTHORIZATION)
                                .apiKey(apiKey)
                                .build();
    }
}

@Component
@RequiredArgsConstructor
class InterceptorAppConfig implements WebMvcConfigurer {

    static final String[] SECURED_APIS = {"/v1/**"};

    private final ApiKeyInterceptor apiKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MappedInterceptor(SECURED_APIS, apiKeyInterceptor));
    }
}