package com.mms.mms_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends ApiException {
    private static final HttpStatus STATUS_CODE = HttpStatus.BAD_REQUEST;

    private static final ErrorType ERROR_TYPE = ErrorType.INVALID_INPUT;

    public InvalidInputException(String message) {
        super(message, STATUS_CODE, ERROR_TYPE);
    }
}