package com.mms.mms_api.exception;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

/**
 * Standard API error response payload.
 */
@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> messages;

    /**
     * Creates an error response and stamps it with the current timestamp.
     *
     * @param status HTTP status code
     * @param error error type value
     * @param messages field/message map
     */
    public ErrorResponse(int status, String error, Map<String, String> messages) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.messages = messages;
    }
}