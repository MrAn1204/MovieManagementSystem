package com.mms.mms_api.business.handler.seat;

import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.SeatType;
import com.mms.mms_api.util.mapper.SeatMapper;
import com.mms.mms_api.util.validator.SeatValidator;

public class SeatUpdateHandler extends SeatBaseHandler<SeatUpdateCommand, SeatDto> {
    private final RoomRepository roomRepository;

    public SeatUpdateHandler(SeatUpdateCommand request, SeatMapper seatMapper, SeatRepository seatRepository,
            RoomRepository roomRepository) {
        super(request, seatMapper, seatRepository);
        this.roomRepository = roomRepository;
    }

    @Override
    @Transactional
    public SeatDto execute() {
        Seat seat = seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        SeatValidator.validateSeatType(request.getSeatType());

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        Seat secondSeat = seatRepository.findFirstByLinkedSeat(seat);

        SeatType oldSeatType = seat.getSeatType();
        SeatType newSeatType = SeatType.valueOf(request.getSeatType());

        seatMapper.updateEntity(request, seat);
        
        Seat savedSeat = seatRepository.save(seat);

        if (oldSeatType == SeatType.COUPLE && newSeatType != SeatType.COUPLE) {
            secondSeat.setSeatType(SeatType.STANDARD);
            secondSeat.setLinkedSeat(null);
        } else if (oldSeatType != SeatType.COUPLE && newSeatType == SeatType.COUPLE) {
            secondSeat = linkCoupleSeat(savedSeat, room);
        }


        if (secondSeat != null) {
            seatRepository.save(secondSeat);
        }

        return seatMapper.toDto(savedSeat);
    }
}