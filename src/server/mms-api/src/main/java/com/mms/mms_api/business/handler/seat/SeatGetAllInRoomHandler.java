package com.mms.mms_api.business.handler.seat;

import java.util.List;

import com.mms.mms_api.business.query.seat.SeatGetAllInRoomQuery;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.SeatMapper;

public class SeatGetAllInRoomHandler extends SeatBaseHandler<SeatGetAllInRoomQuery, List<SeatDto>> {
    public SeatGetAllInRoomHandler(SeatGetAllInRoomQuery request, SeatMapper seatMapper, SeatRepository seatRepository) {
        super(request, seatMapper, seatRepository);
    }

    @Override
    public List<SeatDto> execute() {
        List<Seat> seats = seatRepository.findByRoomId(request.getRoomId());

        if (seats == null || seats.isEmpty()) {
            throw new ResourceNotFoundException("room.notFound");
        }

        return seats.stream().map(seatMapper::toDto).toList();
    }
    
}
