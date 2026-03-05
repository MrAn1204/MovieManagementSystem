package com.mms.mms_api.business.query.room;

import com.mms.mms_api.business.query.BaseSearchQuery;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomSearchQuery extends BaseSearchQuery {
    @PositiveOrZero(message = "{search.roomCapacityRange.invalid}")
    private int minCapacity;

    @PositiveOrZero(message = "{search.roomCapacityRange.invalid}")
    private int maxCapacity;

    @AssertTrue(message = "{search.roomCapacityRange.invalid}")
    public boolean isCapacityValid() {
        return maxCapacity >= minCapacity || maxCapacity == 0;
    }
}
