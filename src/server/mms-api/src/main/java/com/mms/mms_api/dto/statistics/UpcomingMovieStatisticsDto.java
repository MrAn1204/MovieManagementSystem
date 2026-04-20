package com.mms.mms_api.dto.statistics;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;

/**
 * DTO row for upcoming movie statistics.
 */
@Data
public class UpcomingMovieStatisticsDto {
    private UUID id;

    private String name;

    private LocalDate releaseDate;
}