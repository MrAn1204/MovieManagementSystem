package com.mms.mms_api.dto.schedule;

import java.time.LocalDateTime;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleDto extends BaseDto {
    private String name;

    private LocalDateTime showTime;

    private String movie;

    private String room;
}