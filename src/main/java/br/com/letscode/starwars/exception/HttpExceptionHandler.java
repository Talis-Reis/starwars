package br.com.letscode.starwars.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusinessException(BusinessException exception, WebRequest request) {
        var validatorError = exception.getType();
        var statusCode = validatorError.getStatus();
        var error = new ClientHttpError(exception.getMessage(), validatorError.getName(), statusCode.value());

        return handleExceptionInternal(exception, error, new HttpHeaders(), statusCode, request);
    }
}

