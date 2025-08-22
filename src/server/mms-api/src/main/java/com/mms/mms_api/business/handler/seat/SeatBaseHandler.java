package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.util.mapper.SeatMapper;

public abstract class SeatBaseHandler<I, O> extends BaseHandler<I, O> {
    protected final SeatRepository seatRepository;

    protected final SeatMapper seatMapper;

    protected SeatBaseHandler(I request, SeatMapper seatMapper, SeatRepository seatRepository) {
        super(request);
        this.seatMapper = seatMapper;
        this.seatRepository = seatRepository;
    }
}