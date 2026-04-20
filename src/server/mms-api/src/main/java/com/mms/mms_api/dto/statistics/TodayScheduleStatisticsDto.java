package com.mms.mms_api.dto.statistics;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

/**
 * DTO row for today's schedule statistics.
 */
@Data
public class TodayScheduleStatisticsDto {
    private UUID id;

    private String movieName;

    private LocalDateTime showTime;

    private String roomName;
}