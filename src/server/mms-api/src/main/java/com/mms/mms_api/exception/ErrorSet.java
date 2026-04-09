package com.mms.mms_api.exception;

import java.util.HashSet;

public class ErrorSet extends HashSet<ErrorDetail> {
    public void add(String field, String message, Object... args) {
        this.add(new ErrorDetail(field, message, args));
    }
    
    public void throwIfNotEmpty(ErrorType errorType) {
        if (isEmpty()) {
            return;
        }

        switch (errorType) {
            case RESOURCE_NOT_FOUND:
                throw new ResourceNotFoundException(this);
            case INVALID_INPUT:
                throw new InvalidInputException(this);
            default:
                throw new InvalidInputException(this);
        }
    }
}
