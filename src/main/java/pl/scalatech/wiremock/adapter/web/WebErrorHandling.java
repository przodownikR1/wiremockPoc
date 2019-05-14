package pl.scalatech.wiremock.adapter.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.badRequest;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
class WebErrorHandling {
    private static final String ERROR_UNEXPECTED_EXCEPTION = "error.unexpectedException";
    private final MessageSource messageSource;
    private static final String ERR_REQUEST = "Request: ";
    private static final String ERR_RAISED = "  raised: ";

    @ResponseBody
    @ExceptionHandler({Exception.class, RuntimeException.class})
    ResponseEntity<String> unexpectedExceptionHandler(HttpServletRequest request, Exception ex, Locale locale) {
        log.error(ERR_REQUEST + request.getRequestURL() + ERR_RAISED + ex.getMessage(), ex);
        return badRequest().body(messageSource.getMessage(ERROR_UNEXPECTED_EXCEPTION, null, locale));
    }


    @ResponseBody
    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
    ResponseEntity<String> authenticationExceptionHandler(HttpServletRequest request, HttpClientErrorException.Unauthorized ex) {
        log.error(ERR_REQUEST + request.getRequestURL() + ERR_RAISED + ex.getMessage(), ex);
        return ResponseEntity.status(UNAUTHORIZED)
                             .body(ex.getMessage());
    }
}
