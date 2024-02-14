package org.jaco114.repolookup.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        LinkedHashMap<String, Object> body = new LinkedHashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        body.put("status", status.value());
        body.put("errors", errors);

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;

        LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<?> webClientResponseException(WebClientResponseException ex) {

        HttpStatusCode status = ex.getStatusCode();

        LinkedHashMap<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, status);
    }

}