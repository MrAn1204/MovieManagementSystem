package com.mms.mms_api.exception;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
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
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                ErrorType.AUTHENTICATION_FAILED.getValue(),
                Map.of("message", messageService.getByCode("auth.credentials.incorrect")));

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                ErrorType.ACCESS_DENIED.getValue(),
                Map.of("message", messageService.getByCode("auth.access.denied")));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        Throwable cause = exception.getMostSpecificCause();
        
        if (cause instanceof InvalidFormatException ife) {
            String path = ife.getPath().get(0).getFieldName();

            String message = messageService.getByCode("field.invalid");
            
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    ErrorType.INVALID_INPUT.getValue(),
                    Map.of(path, message));

            return ResponseEntity.badRequest().body(errorResponse);
        }

        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        String path = exception.getName();

        String message = messageService.getByCode("field.invalid");
            
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorType.INVALID_INPUT.getValue(),
                Map.of(path, message));

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorType.SERVER_ERROR.getValue(),
                Map.of("message", messageService.getByCode("error.general")));

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
