package com.mms.mms_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
    @NonNull
    protected final HttpStatus statusCode;

    protected final ErrorType errorType;

    @NonNull
    protected final String messageKey;

    protected final transient Object[] args;

    protected ApiException(String messageKey, HttpStatus statusCode, ErrorType errorType, Object... args) {
        super();
        this.statusCode = statusCode != null ? statusCode : HttpStatus.BAD_REQUEST;
        this.errorType = errorType;
        this.messageKey = messageKey != null ? messageKey : "error.general";
        this.args = args;
    }
}
