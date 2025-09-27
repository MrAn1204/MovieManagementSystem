package com.mms.mms_api.business.query.ticket;

import com.mms.mms_api.business.query.BaseGetByIdQuery;

import java.util.UUID;

public class TicketGetByIdQuery extends BaseGetByIdQuery {
    public TicketGetByIdQuery(UUID id) {
        super(id);
    }
}