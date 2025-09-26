package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.query.seat.SeatGetAllQuery;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.util.mapper.SeatMapper;

import java.util.List;

public class SeatGetAllHandler extends SeatBaseHandler<SeatGetAllQuery, List<SeatDto>> {
    public SeatGetAllHandler(SeatGetAllQuery request, SeatMapper seatMapper, SeatRepository seatRepository) {
        super(request, seatMapper, seatRepository);
    }

    @Override
    public List<SeatDto> execute() {
        return seatRepository.findByLinkedSeatIsNull().stream().map(seatMapper::toDto).toList();
    }
}