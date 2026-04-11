package com.mms.mms_api.business.query.schedule;

import java.util.UUID;

import com.mms.mms_api.business.query.BaseGetByIdQuery;

/**
 * Query payload for retrieving schedule by id.
 */
public class ScheduleGetByIdQuery extends BaseGetByIdQuery {
    public ScheduleGetByIdQuery(UUID id) {
        super(id);
    }
}
