package com.mms.mms_api.exception;

import lombok.Data;

@Data
public class ErrorDetail {
    private String field;

    private String messageKey;

    private Object[] messageParams;

    public ErrorDetail(String field, String messageKey, Object... messageParams) {
        this.field = field;
        this.messageKey = messageKey;
        this.messageParams = messageParams;
    }
}
