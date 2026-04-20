package com.mms.mms_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.query.statistics.StatisticsSummaryQuery;
import com.mms.mms_api.business.service.StatisticsService;
import com.mms.mms_api.dto.statistics.StatisticsSummaryDto;

import lombok.AllArgsConstructor;

/**
 * Provides dashboard statistics endpoints.
 */
@RestController
@RequestMapping("/api/statistics")
@AllArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    /**
     * Returns the dashboard statistics summary payload.
     */
    @GetMapping("/summary")
    public ResponseEntity<StatisticsSummaryDto> getSummary() {
        StatisticsSummaryDto summary = statisticsService.handle(new StatisticsSummaryQuery());

        return ResponseEntity.ok(summary);
    }
}