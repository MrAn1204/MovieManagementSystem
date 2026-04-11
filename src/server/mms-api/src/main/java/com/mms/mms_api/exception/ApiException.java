package com.mms.mms_api.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import lombok.Getter;

/**
 * Base runtime exception for API-level business and validation errors.
 */
@Getter
public abstract class ApiException extends RuntimeException {
    @NonNull
    protected final HttpStatus statusCode;

    protected final ErrorType errorType;

    protected final transient Set<ErrorDetail> messages;

    protected final transient Object[] args;

    /**
     * Creates an API exception with one or more field-level details.
     *
     * @param messages error details
     * @param statusCode HTTP status to return
     * @param errorType API error type
     * @param args optional message formatting arguments
     */
    protected ApiException(Set<ErrorDetail> messages, HttpStatus statusCode, ErrorType errorType, Object... args) {
        super();
        this.statusCode = statusCode != null ? statusCode : HttpStatus.BAD_REQUEST;
        this.errorType = errorType;
        this.messages = messages;
        this.args = args;
    }

    /**
     * Creates an API exception with a single message code.
     *
     * @param message message code
     * @param statusCode HTTP status to return
     * @param errorType API error type
     * @param args optional message formatting arguments
     */
    protected ApiException(String message, HttpStatus statusCode, ErrorType errorType, Object... args) {
        super(message);
        this.statusCode = statusCode != null ? statusCode : HttpStatus.BAD_REQUEST;
        this.errorType = errorType;
        this.messages = null;
        this.args = args;
    }
}
