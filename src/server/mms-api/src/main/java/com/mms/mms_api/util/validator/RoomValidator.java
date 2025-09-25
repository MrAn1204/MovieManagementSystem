package com.mms.mms_api.util.validator;

import com.mms.mms_api.exception.InvalidInputException;

public class RoomValidator {
    private RoomValidator() {}

    public static void validateSeatQuantity(int seatQuantity) {
        if (seatQuantity <= 0) {
            throw new InvalidInputException("room.seatQuantity.invalid");
        }
    }
}
