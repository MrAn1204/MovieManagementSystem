package com.mms.mms_api.business.handler.seat;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.business.query.seat.SeatTypeGetAllQuery;
import com.mms.mms_api.model.SeatType;

/**
 * Handles requests to retrieve seat type metadata.
 */
@Component
public class SeatTypeGetAllHandler extends BaseHandler<SeatTypeGetAllQuery, Map<String, Double>> {
    /**
     * Creates a SeatTypeGetAllHandler.
     */
    public SeatTypeGetAllHandler() {
        super();
    }

    /**
     * Returns all seat types with their associated pricing multipliers.
     *
     * @param request query object
     * @return map of seat type names to pricing multipliers
     */
    @Override
    public Map<String, Double> execute(SeatTypeGetAllQuery request) {
        Map<String, Double> seatTypes = new HashMap<>();

        for (SeatType seatType : SeatType.values()) {
            seatTypes.put(seatType.name(), seatType.getMultiplier());
        }

        return seatTypes;
    }
}
