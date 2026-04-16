package com.mms.mms_api.business.query.promotion;

import java.time.LocalDate;

import com.mms.mms_api.business.query.BaseSearchQuery;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Query payload for searching promotions.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PromotionSearchQuery extends BaseSearchQuery{
    private LocalDate startDate;
    
    private LocalDate endDate;

    @AssertTrue(message = "{search.dateRange.invalid}")
    private boolean isValidDateRange() {
        if (startDate != null && endDate != null) {
            return startDate.isBefore(endDate);
        }
        return true;
    }
}
