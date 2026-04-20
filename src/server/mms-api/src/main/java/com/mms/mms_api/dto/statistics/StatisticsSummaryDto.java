package com.mms.mms_api.dto.statistics;

import java.util.List;

import lombok.Data;

/**
 * DTO containing compact dashboard statistics.
 */
@Data
public class StatisticsSummaryDto {
    private long movieCount;

    private long ticketCount;

    private long roomCount;

    private long scheduleCount;

    private long promotionCount;

    private long userCount;

    private List<Long> monthlyTicketsSold;

    private List<UpcomingMovieStatisticsDto> upcomingMovies;

    private List<TodayScheduleStatisticsDto> todaySchedules;
}