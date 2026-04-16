package com.mms.mms_api.business.handler.seat;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.seat.SeatGetAllQuery;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.seat.SeatDto;
import com.mms.mms_api.util.mapper.SeatMapper;

import java.util.List;

/**
 * Handles requests to retrieve all seats.
 */
@Component
public class SeatGetAllHandler extends SeatBaseHandler<SeatGetAllQuery, List<SeatDto>> {
    /**
     * Creates a SeatGetAllHandler.
     *
     * @param seatMapper seat mapper
     * @param seatRepository seat repository
     */
    public SeatGetAllHandler(SeatMapper seatMapper, SeatRepository seatRepository) {
        super(seatMapper, seatRepository);
    }

    /**
     * Retrieves all primary seats (linked couple seats are excluded).
     *
     * @param request query object
     * @return list of seat DTOs
     */
    @Override
    public List<SeatDto> execute(SeatGetAllQuery request) {
        return seatRepository.findByLinkedSeatIsNull().stream().map(seatMapper::toDto).toList();
    }
}