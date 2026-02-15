package com.mms.mms_api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
    @NonNull
    protected final HttpStatus statusCode;

    protected final ErrorType errorType;

    protected final transient List<ErrorDetail> messages;

    protected final transient Object[] args;

    protected ApiException(List<ErrorDetail> messages, HttpStatus statusCode, ErrorType errorType, Object... args) {
        super();
        this.statusCode = statusCode != null ? statusCode : HttpStatus.BAD_REQUEST;
        this.errorType = errorType;
        this.messages = messages;
        this.args = args;
    }

    protected ApiException(String message, HttpStatus statusCode, ErrorType errorType, Object... args) {
        super(message);
        this.statusCode = statusCode != null ? statusCode : HttpStatus.BAD_REQUEST;
        this.errorType = errorType;
        this.messages = null;
        this.args = args;
    }
}
