package com.mms.mms_api.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailException extends ApiException {
    private static final HttpStatus STATUS_CODE = HttpStatus.INTERNAL_SERVER_ERROR;

    private static final ErrorType ERROR_TYPE = ErrorType.SERVER_ERROR;

    public EmailException(String messageKey) {
        super(messageKey, STATUS_CODE, ERROR_TYPE);
    }

    public EmailException(Set<ErrorDetail> messages) {
        super(messages, STATUS_CODE, ERROR_TYPE);
    }
}
