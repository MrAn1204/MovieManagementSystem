package com.mms.mms_api.business.handler.room;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.util.mapper.RoomMapper;

public abstract class RoomBaseHandler<I, O> extends BaseHandler<I, O> {
    protected RoomMapper roomMapper;

    protected RoomRepository roomRepository;

    protected RoomBaseHandler(I request, RoomMapper roomMapper, RoomRepository roomRepository) {
        super(request);
        this.roomMapper = roomMapper;
        this.roomRepository = roomRepository;
    }
}
