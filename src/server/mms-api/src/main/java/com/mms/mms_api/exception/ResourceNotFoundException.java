package com.mms.mms_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends ApiException {
    private static final int STATUS_CODE = HttpStatus.NOT_FOUND.value();

    private static final String ERROR_TYPE = ErrorType.RESOURCE_NOT_FOUND.getValue();

    public ResourceNotFoundException(String messageKey) {
        super(messageKey, STATUS_CODE, ERROR_TYPE);
    }
}
