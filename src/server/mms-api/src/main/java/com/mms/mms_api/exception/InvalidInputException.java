package com.mms.mms_api.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends ApiException {
    private static final HttpStatus STATUS_CODE = HttpStatus.BAD_REQUEST;

    private static final ErrorType ERROR_TYPE = ErrorType.INVALID_INPUT;

    public InvalidInputException(String messageKey) {
        super(messageKey, STATUS_CODE, ERROR_TYPE);
    }

    public InvalidInputException(Set<ErrorDetail> messages) {
        super(messages, STATUS_CODE, ERROR_TYPE);
    }
}