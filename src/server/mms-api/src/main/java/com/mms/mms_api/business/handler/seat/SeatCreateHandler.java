package com.mms.mms_api.business.handler.seat;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.seat.SeatDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.SeatType;
import com.mms.mms_api.util.mapper.SeatMapper;

@Component
public class SeatCreateHandler extends SeatBaseHandler<SeatCreateCommand, SeatDto> {
    private final RoomRepository roomRepository;

    public SeatCreateHandler(SeatMapper seatMapper, SeatRepository seatRepository,
            RoomRepository roomRepository) {
        super(seatMapper, seatRepository);
        this.roomRepository = roomRepository;
    }

    @Override
    public SeatDto execute(SeatCreateCommand request) {
        Seat seat = seatMapper.toEntity(request);

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        seat.setRoom(room);

        Seat savedSeat = seatRepository.save(seat);

        Seat secondSeat = null;

        if (seat.getSeatType() == SeatType.COUPLE) {
            secondSeat = linkCoupleSeat(savedSeat, room);
        }

        if (secondSeat != null) {
            secondSeat.setLinkedSeat(savedSeat);
            seatRepository.save(secondSeat);
        }

        return seatMapper.toDto(savedSeat);
    }

}
