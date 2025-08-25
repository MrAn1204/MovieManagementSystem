package com.mms.mms_api.util.validator;

import org.springframework.util.StringUtils;

import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.SeatType;

public class SeatValidator {
    private SeatValidator() {}
    
    public static void validatePosition(int seatQuantity, int seatColumn, int seatRow) {
        int numberOfColumns = seatQuantity / AppConstant.COLUMN_MAX;

        if (seatColumn <= 0 || seatColumn > numberOfColumns) {
            throw new InvalidInputException(ErrorMessage.COLUMN_INVALID);
        }

        if (seatRow <= 0 || seatRow > 10) {
            throw new InvalidInputException(ErrorMessage.ROW_INVALID);
        }
    }

    public static void validateSeatType(SeatType seatType) {
        if (seatType == null) {
            throw new InvalidInputException(ErrorMessage.SEAT_TYPE_INVALID);
        }
    }

    public static void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidInputException(ErrorMessage.NAME_REQUIRED);
        }
    }
}
