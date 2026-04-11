package com.mms.mms_api.business.query.room;

import java.util.UUID;

import com.mms.mms_api.business.query.BaseGetByIdQuery;

/**
 * Query payload for retrieving room by id.
 */
public class RoomGetByIdQuery extends BaseGetByIdQuery {
    public RoomGetByIdQuery(UUID id) {
        super(id);
    }
}
