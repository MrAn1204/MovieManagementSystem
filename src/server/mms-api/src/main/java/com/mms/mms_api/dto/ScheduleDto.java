package com.mms.mms_api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ScheduleDto {
    private UUID id;

    private String showTime;

    private String movieName;

    private String roomName;
}