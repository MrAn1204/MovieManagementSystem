package com.mms.mms_api.business.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mms.mms_api.business.query.statistics.StatisticsSummaryQuery;
import com.mms.mms_api.dto.statistics.StatisticsSummaryDto;
import com.mms.mms_api.mediator.RequestMediator;

import lombok.RequiredArgsConstructor;

/**
 * Provides dashboard statistics operations.
 */
@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final RequestMediator mediator;

    private StatisticsSummaryDto cachedSummary;

    /**
     * Returns dashboard statistics summary.
     */
    public StatisticsSummaryDto getSummary() {
        return cachedSummary;
    }

    @Scheduled(fixedRate = 3600000)
    private void refreshCache() {
        cachedSummary = mediator.execute(new StatisticsSummaryQuery());
    }
}