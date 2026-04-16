package com.mms.mms_api.business.handler.room;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.util.mapper.RoomMapper;

/**
 * Base handler for room-related requests.
 */
public abstract class RoomBaseHandler<I, O> extends BaseHandler<I, O> {
    protected RoomMapper roomMapper;

    protected RoomRepository roomRepository;

    /**
     * Creates a room base handler.
     *
     * @param roomMapper room mapper
     * @param roomRepository room repository
     */
    protected RoomBaseHandler(RoomMapper roomMapper, RoomRepository roomRepository) {
        this.roomMapper = roomMapper;
        this.roomRepository = roomRepository;
    }
}
