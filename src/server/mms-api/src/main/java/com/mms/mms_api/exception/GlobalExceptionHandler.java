package com.mms.mms_api.exception;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + " " + msg2)
                .orElse("Validation failed");
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorType.CONSTRAINT_VIOLATION.getValue(),
                errorMessages);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception) {
        String message = messageSource.getMessage(exception.getMessage(), exception.getArgs(),
                LocaleContextHolder.getLocale());

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                exception.getStatusCode(),
                exception.getErrorType(),
                message);

        return ResponseEntity.status(HttpStatus.valueOf(exception.getStatusCode())).body(errorResponse);
    }
}
