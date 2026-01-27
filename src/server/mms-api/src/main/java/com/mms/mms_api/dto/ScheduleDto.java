package com.mms.mms_api.dto;

import java.time.LocalDateTime;

import com.mms.mms_api.common.IdNameDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleDto extends BaseDto {
    private String name;

    private LocalDateTime showTime;

    private IdNameDto movie;

    private IdNameDto room;
}