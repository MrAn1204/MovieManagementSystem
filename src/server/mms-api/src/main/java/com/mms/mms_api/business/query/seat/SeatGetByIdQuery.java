package com.mms.mms_api.business.query.seat;

import java.util.UUID;

import com.mms.mms_api.business.query.BaseGetByIdQuery;

/**
 * Query payload for retrieving seat by id.
 */
public class SeatGetByIdQuery extends BaseGetByIdQuery {

    public SeatGetByIdQuery(UUID id) {
        super(id);
    }
    
}
