package com.mms.mms_api.exception;

import lombok.Data;

/**
 * Field-level error detail used in structured API exception responses.
 */
@Data
public class ErrorDetail {
    private String field;

    private String messageKey;

    private Object[] messageParams;

    /**
     * Creates an error detail.
     *
     * @param field failing field name
     * @param messageKey i18n key for the error message
     * @param messageParams optional parameters for message formatting
     */
    public ErrorDetail(String field, String messageKey, Object... messageParams) {
        this.field = field;
        this.messageKey = messageKey;
        this.messageParams = messageParams;
    }
}
