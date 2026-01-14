package com.mms.mms_api.business.query.ticket;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mms.mms_api.business.query.BaseSearchQuery;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketSearchQuery extends BaseSearchQuery {
    private LocalDateTime showTime;

    private UUID movieId;

    private UUID roomId;

    private UUID promotionId;
}
