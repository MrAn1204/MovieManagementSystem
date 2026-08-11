package com.mms.mms_api.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Centralized REST exception mapping for translating runtime errors into API responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String DEFAULT_ERROR_KEY = "message";

    private final AppMessageService messageService;

    /**
     * Creates an exception handler with localized message support.
     *
     * @param messageService message lookup service
     */
    public GlobalExceptionHandler(AppMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Handles bean validation errors raised by request body validation.
     *
     * @param exception validation exception
     * @return bad-request error response with field messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        @SuppressWarnings("null")
        Map<String, String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorType.CONSTRAINT_VIOLATION.getValue(),
                errorMessages);

        logger.debug("Failed validation for request body at {} {}: {}", request.getMethod(), request.getRequestURI(), exception.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Handles custom API exceptions from business logic.
     *
     * @param exception API exception
     * @return mapped error response with status from exception
     */
    @SuppressWarnings("null")
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException exception, HttpServletRequest request) {
        Map<String, String> messages;
        if (exception.getMessages() != null) {
            messages = exception.getMessages().stream()
                    .collect(Collectors.toMap(
                            ErrorDetail::getField,
                            detail -> messageService.getByCode(detail.getMessageKey(), detail.getMessageParams())));
        } else {
            messages = Map.of(DEFAULT_ERROR_KEY, messageService.getByCode(exception.getMessage()));
        }

        HttpStatus statusCode = exception.getStatusCode();

        ErrorResponse errorResponse = new ErrorResponse(
                statusCode.value(),
                exception.getErrorType().getValue(),
                messages);

        logger.error("Failed handling request at {} {}", request.getMethod(), request.getRequestURI(), exception);

        return ResponseEntity.status(statusCode).body(errorResponse);
    }

    /**
     * Handles authentication failures.
     *
     * @param exception authentication exception
     * @return unauthorized response
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ErrorType.AUTHENTICATION_FAILED.getValue(),
                Map.of(DEFAULT_ERROR_KEY, messageService.getByCode("auth.credentials.incorrect")));

        logger.debug("Authentication failed at {} {}: {}", request.getMethod(), request.getRequestURI(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Handles authorization failures for authenticated users.
     *
     * @param exception access denied exception
     * @return forbidden response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                ErrorType.ACCESS_DENIED.getValue(),
                Map.of(DEFAULT_ERROR_KEY, messageService.getByCode("auth.access.denied")));

        logger.debug("Access denied at {} {}: {}", request.getMethod(), request.getRequestURI(), exception.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }


    /**
     * Handles JWT parsing and validation errors.
     *
     * @param exception JWT exception
     * @return unauthorized response
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException exception, HttpServletRequest request) {
        String message = messageService.getByCode("auth.credentials.invalid");
            
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                ErrorType.ACCESS_DENIED.getValue(),
                Map.of(DEFAULT_ERROR_KEY, message));

        logger.debug("JWT validation failed at {} {}: {}", request.getMethod(), request.getRequestURI(), exception.getMessage());
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Handles malformed JSON or request payload values.
     *
     * @param exception request body parsing exception
     * @return bad-request response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        Throwable cause = exception.getMostSpecificCause();
        
        if (cause instanceof InvalidFormatException ife) {
            String path = ife.getPath().get(0).getFieldName();

            String message = messageService.getByCode("field.invalid");
            
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ErrorType.INVALID_INPUT.getValue(),
                    Map.of(path, message));

            logger.debug("Invalid format for request body at {} {}: {}", request.getMethod(), request.getRequestURI(), exception.getMessage());

            return ResponseEntity.badRequest().body(errorResponse);
        }

        logger.debug("Malformed request body at {} {}: {}", request.getMethod(), request.getRequestURI(), exception.getMessage());

        return ResponseEntity.badRequest().build();
    }

    /**
     * Handles invalid URL/query parameter type mismatches.
     *
     * @param exception type mismatch exception
     * @return bad-request response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {
        String path = exception.getName();

        String message = messageService.getByCode("field.invalid");
            
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ErrorType.INVALID_INPUT.getValue(),
                Map.of(path, message));

        logger.debug("Method argument type mismatch at {} {}: {}", request.getMethod(), request.getRequestURI(), exception.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    /**
     * Handles all unhandled runtime exceptions.
     *
     * @param exception unhandled exception
     * @return internal-server-error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorType.SERVER_ERROR.getValue(),
                Map.of(DEFAULT_ERROR_KEY, messageService.getByCode("error.general")));

        logger.error("Unhandled exception at {} {}", request.getMethod(), request.getRequestURI(), exception);

        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
