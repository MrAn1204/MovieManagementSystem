package com.mms.mms_api.business.query.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

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

    private String room;

    @AssertTrue
    public boolean isTimeRangeValid() {
        return date == null || minTime == null || maxTime == null
                || (minTime.isBefore(maxTime) || minTime.equals(maxTime));
    }
}
