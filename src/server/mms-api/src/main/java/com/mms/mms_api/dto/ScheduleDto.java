package com.mms.mms_api.dto;

import java.util.UUID;

import com.mms.mms_api.common.IdNameDto;

import lombok.Data;

@Data
public class ScheduleDto {
    private UUID id;

    private String showTime;

    private IdNameDto movie;

    private IdNameDto room;
}