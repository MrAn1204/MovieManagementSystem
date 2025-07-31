package com.mms.mms_api.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ErrorType.RESOURCE_NOT_FOUND.getValue(),
                exception.getMessage());
    }

    @ExceptionHandler(InvalidInputException.class)
    public ErrorResponse handleInvalidInputException(InvalidInputException exception) {
        return new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorType.INVALID_INPUT.getValue(),
                exception.getMessage());
    }
}
