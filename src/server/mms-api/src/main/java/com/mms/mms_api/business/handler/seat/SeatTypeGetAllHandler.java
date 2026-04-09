package com.mms.mms_api.business.handler.seat;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.business.query.seat.SeatTypeGetAllQuery;
import com.mms.mms_api.model.SeatType;

@Component
public class SeatTypeGetAllHandler extends BaseHandler<SeatTypeGetAllQuery, Map<String, Double>> {
    public SeatTypeGetAllHandler() {
        super();
    }

    @Override
    public Map<String, Double> execute(SeatTypeGetAllQuery request) {
        Map<String, Double> seatTypes = new HashMap<>();

        for (SeatType seatType : SeatType.values()) {
            seatTypes.put(seatType.name(), seatType.getMultiplier());
        }

        return seatTypes;
    }
}
