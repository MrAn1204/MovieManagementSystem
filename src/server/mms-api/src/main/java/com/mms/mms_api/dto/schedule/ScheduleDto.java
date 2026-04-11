package com.mms.mms_api.dto.schedule;

import java.time.LocalDateTime;

import com.mms.mms_api.common.IdNameDto;
import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing schedule summary information.
 *
 * Maps from {@link com.mms.mms_api.model.Schedule Schedule}.
 * Includes movie and room information.
 *
 * @see com.mms.mms_api.model.Schedule Schedule
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleDto extends BaseDto {
    private String name;

    private LocalDateTime showTime;

    private IdNameDto movie;

    private IdNameDto room;
}