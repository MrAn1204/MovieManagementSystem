package com.mms.mms_api.business.query.room;

import com.mms.mms_api.business.query.BaseSearchQuery;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomSearchQuery extends BaseSearchQuery {
    @PositiveOrZero(message = "{search.seatQuantityRange.invalid}")
    private int seatQuantityMin;

    @PositiveOrZero(message = "{search.seatQuantityRange.invalid}")
    private int seatQuantityMax;

    @AssertTrue(message = "{search.seatQuantityRange.invalid}")
    public boolean isSeatQuantityValid() {
        return seatQuantityMax >= seatQuantityMin || seatQuantityMax == 0;
    }
}
