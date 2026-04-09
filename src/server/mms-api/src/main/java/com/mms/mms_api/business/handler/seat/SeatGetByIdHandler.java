package com.mms.mms_api.business.handler.seat;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.seat.SeatGetByIdQuery;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.seat.SeatDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.util.mapper.SeatMapper;

@Component
public class SeatGetByIdHandler extends SeatBaseHandler<SeatGetByIdQuery, SeatDto> {
    public SeatGetByIdHandler(SeatMapper seatMapper, SeatRepository seatRepository) {
        super(seatMapper, seatRepository);
    }

    @Override
    public SeatDto execute(SeatGetByIdQuery request) {
        return seatRepository.findById(request.getId())
                .map(seatMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));
    }
}