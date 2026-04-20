package com.mms.mms_api.business.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.mms.mms_api.business.query.statistics.StatisticsSummaryQuery;
import com.mms.mms_api.dto.statistics.StatisticsSummaryDto;
import com.mms.mms_api.mediator.RequestMediator;

import lombok.AllArgsConstructor;

/**
 * Provides dashboard statistics operations.
 */
@Service
@AllArgsConstructor
public class StatisticsService {
    private final RequestMediator mediator;

    /**
     * Returns dashboard statistics summary.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public StatisticsSummaryDto handle(StatisticsSummaryQuery request) {
        return mediator.execute(request);
    }
}