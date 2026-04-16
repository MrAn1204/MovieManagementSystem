package com.mms.mms_api.business.handler.seat;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.seat.SeatGetByIdQuery;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.seat.SeatDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.util.mapper.SeatMapper;

/**
 * Handles requests to retrieve seat by id.
 */
@Component
public class SeatGetByIdHandler extends SeatBaseHandler<SeatGetByIdQuery, SeatDto> {
    /**
     * Creates a SeatGetByIdHandler.
     *
     * @param seatMapper seat mapper
     * @param seatRepository seat repository
     */
    public SeatGetByIdHandler(SeatMapper seatMapper, SeatRepository seatRepository) {
        super(seatMapper, seatRepository);
    }

    /**
     * Retrieves a seat by its identifier.
     *
     * @param request query containing the target seat id
     * @return seat DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the seat does not exist
     */
    @Override
    public SeatDto execute(SeatGetByIdQuery request) {
        return seatRepository.findById(request.getId())
                .map(seatMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));
    }
}