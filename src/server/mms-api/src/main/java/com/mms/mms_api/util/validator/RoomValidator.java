package com.mms.mms_api.util.validator;

import org.springframework.util.StringUtils;

import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.InvalidInputException;

public class RoomValidator {
    private RoomValidator() {}

    public static void validateSeatQuantity(int seatQuantity) {
        if (seatQuantity <= 0) {
            throw new InvalidInputException(ErrorMessage.QUANTITY_INVALID);
        }
    }

    public static void validateName(String name) {
        if (StringUtils.hasText(name)) {
            throw new InvalidInputException(ErrorMessage.NAME_REQUIRED);
        }
    }
}
