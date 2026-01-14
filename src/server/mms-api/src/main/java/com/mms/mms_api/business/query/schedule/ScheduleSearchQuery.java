package com.mms.mms_api.business.query.schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import com.mms.mms_api.business.query.BaseSearchQuery;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleSearchQuery extends BaseSearchQuery {
private LocalDate date;

    private LocalTime minTime;

    private LocalTime maxTime;

    private UUID roomId;

    @AssertTrue(message = "{search.timeRange.invalid}")
    public boolean isTimeRangeValid() {
        if (date != null && maxTime != null && minTime != null) {
            return minTime.isBefore(maxTime) || minTime.equals(maxTime);
        }
        return true;
    }
}
