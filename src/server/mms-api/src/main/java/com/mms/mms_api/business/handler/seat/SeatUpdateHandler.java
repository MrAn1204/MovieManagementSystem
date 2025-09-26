package com.mms.mms_api.business.handler.seat;

import java.util.List;

import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.SeatDto;
import com.mms.mms_api.exception.InvalidInputException;
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
    public SeatDto execute() {
        Seat seat = seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        SeatValidator.validateSeatType(request.getSeatType());

        seatMapper.updateEntity(request, seat);

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        List<Seat> existingSeats = room.getSeats();

        Seat secondSeat = null;

        if (seat.getSeatType() == SeatType.COUPLE) {
            SeatValidator.validateCoupleSeatPosition(request.getSeatColumn(), room.getSeatQuantity());

            secondSeat = existingSeats.stream()
                    .filter(s -> s.getSeatColumn() == request.getSeatColumn() + 1
                            && s.getSeatRow() == request.getSeatRow())
                    .findFirst()
                    .orElse(null);

            if (secondSeat == null) {
                secondSeat = seatMapper.mapSecondSeat(seat);
            } else if (secondSeat.getSeatType() == SeatType.COUPLE) {
                throw new InvalidInputException("seat.position.invalid");
            } else {
                secondSeat.setName(seat.getName());
                secondSeat.setSeatType(SeatType.COUPLE);
                secondSeat.setLinkedSeat(seat.getId());
            }
        }

        Seat savedSeat = seatRepository.save(seat);

        if (secondSeat != null) {
            seatRepository.save(secondSeat);
        }

        return seatMapper.toDto(savedSeat);
    }
}