package pl.scalatech.wiremock.adapter.web;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
class ApiKeyInterceptor extends HandlerInterceptorAdapter {

    private final String headerName;
    private final String apiKey;

    @Builder
    private static ApiKeyInterceptor create(String headerName, String apiKey) {
        assert headerName.isEmpty() : "Please provide correct header name";
        assert apiKey.isEmpty() : "There is should be apiKey";
        return new ApiKeyInterceptor(headerName, apiKey);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (matchApiKeyHeader(request)) {
            return true;
        }
        throw getExceptionOnInvalidApiKey();
    }

    protected HttpClientErrorException getExceptionOnInvalidApiKey() {
        return HttpClientErrorException.create(UNAUTHORIZED, UNAUTHORIZED.getReasonPhrase(), null, null, null);
    }

    private boolean matchApiKeyHeader(HttpServletRequest request) {
        return Optional.ofNullable(getApiKeyHeader(request))
                       .isPresent();
    }

    private String getApiKeyHeader(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

}