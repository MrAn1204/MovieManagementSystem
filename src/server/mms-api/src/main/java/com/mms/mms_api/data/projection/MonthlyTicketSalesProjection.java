package com.mms.mms_api.data.projection;

/**
 * Projection for monthly sold-ticket aggregate rows.
 */
public interface MonthlyTicketSalesProjection {
    Integer getMonthNumber();

    Long getTicketCount();
}