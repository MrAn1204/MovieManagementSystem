package com.mms.mms_api.business.query.ticket;

import com.mms.mms_api.business.query.BaseGetByIdQuery;

import java.util.UUID;

/**
 * Query payload for retrieving ticket by id.
 */
public class TicketGetByIdQuery extends BaseGetByIdQuery {
    public TicketGetByIdQuery(UUID id) {
        super(id);
    }
}