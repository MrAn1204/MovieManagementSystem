package com.mms.mms_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidInputException extends ApiException {
    private static final int STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    private static final String ERROR_TYPE = ErrorType.INVALID_INPUT.getValue();

    // TODO: Remove this constructor
    public InvalidInputException(ErrorMessage message) {
        super(message.getValue(), STATUS_CODE, ERROR_TYPE);
    }

    public InvalidInputException(String message) {
        super(message, STATUS_CODE, ERROR_TYPE);
    }
}