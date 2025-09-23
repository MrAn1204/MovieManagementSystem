package com.mms.mms_api.exception;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
    protected final int statusCode;

    protected final String errorType;

    protected final String messageKey;

    protected final transient Object[] args;

    protected ApiException(String messageKey, int statusCode, String errorType, Object... args) {
        super(messageKey);
        this.statusCode = statusCode;
        this.errorType = errorType;
        this.messageKey = messageKey;
        this.args = args;
    }
}
