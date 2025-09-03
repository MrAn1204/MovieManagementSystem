package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.SeatMapper;
import com.mms.mms_api.util.validator.SeatValidator;

public class SeatUpdateHandler extends SeatBaseHandler<SeatUpdateCommand, SeatDto> {
    private final RoomRepository roomRepository;

    public SeatUpdateHandler(SeatUpdateCommand request, SeatMapper seatMapper, SeatRepository seatRepository, RoomRepository roomRepository) {
        super(request, seatMapper, seatRepository);
        this.roomRepository = roomRepository;
    }

    @Override
    public SeatDto execute() {
        Seat seat = seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.SEAT_NOT_FOUND));

        SeatValidator.validateName(request.getName());
        SeatValidator.validateSeatType(request.getSeatType());

        seatMapper.updateEntity(request, seat);

        if (request.getRoomId() != null) {
            Room room = roomRepository.findById(request.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.ROOM_NOT_FOUND));

            SeatValidator.validatePosition(room.getSeatQuantity(), request.getSeatColumn(), request.getSeatRow());

            seat.setRoom(room);
        }

        Seat savedSeat = seatRepository.save(seat);

        return seatMapper.toDto(savedSeat);
    }
}