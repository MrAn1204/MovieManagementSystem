package com.mms.mms_api.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mms.mms_api.business.service.AppMessageService;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final AppMessageService messageService;

    public GlobalExceptionHandler(AppMessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        Map<String, String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorType.CONSTRAINT_VIOLATION.getValue(),
                errorMessages);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception) {
        Map<String, String> messages;
        if (exception.getMessages() != null) {
            messages = exception.getMessages().stream()
                    .collect(Collectors.toMap(
                            ErrorDetail::getField,
                            detail -> messageService.getByCode(detail.getMessageKey(), detail.getMessageParams())));
        } else {
            messages = Map.of("message", messageService.getByCode(exception.getMessage()));
        }

        HttpStatus statusCode = exception.getStatusCode();

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                statusCode.value(),
                exception.getErrorType().getValue(),
                messages);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException exception) {
        return handleApiException(new InvalidInputException("auth.credentials.incorrect"));
    }
}
