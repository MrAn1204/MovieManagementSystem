package com.mms.mms_api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ApiException {
    private static final HttpStatus STATUS_CODE = HttpStatus.NOT_FOUND;

    private static final ErrorType ERROR_TYPE = ErrorType.RESOURCE_NOT_FOUND;

    public ResourceNotFoundException(String messageKey) {
        super(messageKey, STATUS_CODE, ERROR_TYPE);
    }

    public ResourceNotFoundException(List<ErrorDetail> messages) {
        super(messages, STATUS_CODE, ERROR_TYPE);
    }
}
