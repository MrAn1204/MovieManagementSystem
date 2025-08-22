package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.query.seat.SeatGetByIdQuery;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.util.mapper.SeatMapper;

public class SeatGetByIdHandler extends SeatBaseHandler<SeatGetByIdQuery, SeatDto> {
    public SeatGetByIdHandler(SeatGetByIdQuery request, SeatMapper seatMapper, SeatRepository seatRepository) {
        super(request, seatMapper, seatRepository);
    }

    @Override
    public SeatDto execute() {
        return seatRepository.findById(request.getId())
                .map(seatMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.SEAT_NOT_FOUND));
    }
}