package com.mms.mms_api.dto.schedule;

import java.util.UUID;

import lombok.Data;

@Data
public class ScheduleSummaryDto {
    private UUID id;

    private String showTime;

    private String roomName;
}
