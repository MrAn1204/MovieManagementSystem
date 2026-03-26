package com.mms.mms_api.business.handler.seat;

import java.util.HashMap;
import java.util.Map;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.business.query.seat.SeatTypeGetAllQuery;
import com.mms.mms_api.model.SeatType;

public class SeatTypeGetAllHandler extends BaseHandler<SeatTypeGetAllQuery, Map<String, Double>> {
    public SeatTypeGetAllHandler(SeatTypeGetAllQuery request) {
        super(request);
    }

    @Override
    public Map<String, Double> execute() {
        Map<String, Double> seatTypes = new HashMap<>();

        for (SeatType seatType : SeatType.values()) {
            seatTypes.put(seatType.name(), seatType.getMultiplier());
        }

        return seatTypes;
    }
}
