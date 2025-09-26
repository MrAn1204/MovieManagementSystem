package com.mms.mms_api.util.validator;

import org.springframework.util.StringUtils;

import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.SeatType;

public class SeatValidator {
    private SeatValidator() {}

    public static void validatePosition(int seatQuantity, int seatColumn, int seatRow) {
        int numberOfColumns = seatQuantity / AppConstant.COLUMN_MAX;

        if (seatColumn <= 0 || seatColumn > numberOfColumns) {
            throw new InvalidInputException("seat.column.invalid");
        }

        if (seatRow <= 0 || seatRow > 10) {
            throw new InvalidInputException("seat.row.invalid");
        }
    }

    public static void validateSeatType(String seatType) {
        if (!StringUtils.hasText(seatType)) {
            throw new InvalidInputException("seat.type.invalid");
        }

        try {
            SeatType.valueOf(seatType);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("seat.type.invalid");
        }
    }

    public static void validateCoupleSeatPosition(int seatColumn, int seatQuantity) {
        int secondColumn = seatColumn + 1;

        int numberOfColumns = seatQuantity / AppConstant.COLUMN_MAX;

        if (secondColumn > numberOfColumns) {
            throw new InvalidInputException("seat.column.invalid");
        }
    }
}
