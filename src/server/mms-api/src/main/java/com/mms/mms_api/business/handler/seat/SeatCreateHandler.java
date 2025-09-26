package com.mms.mms_api.business.handler.seat;

import java.util.List;

import com.mms.mms_api.business.command.seat.SeatCreateCommand;
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

public class SeatCreateHandler extends SeatBaseHandler<SeatCreateCommand, SeatDto> {
    private final RoomRepository roomRepository;

    public SeatCreateHandler(SeatCreateCommand command, SeatMapper seatMapper, SeatRepository seatRepository,
            RoomRepository roomRepository) {
        super(command, seatMapper, seatRepository);
        this.roomRepository = roomRepository;
    }

    @Override
    public SeatDto execute() {
        SeatValidator.validateSeatType(request.getSeatType());

        Seat seat = seatMapper.toEntity(request);

        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));

        List<Seat> existingSeats = room.getSeats();

        if (existingSeats.size() >= room.getSeatQuantity()) {
            throw new InvalidInputException("room.full");
        }
        
        boolean isPositionTaken = existingSeats.stream()
                .anyMatch(s -> s.getSeatColumn() == request.getSeatColumn() && s.getSeatRow() == request.getSeatRow());

        if (isPositionTaken) {
            throw new InvalidInputException("seat.position.invalid");
        }

        SeatValidator.validatePosition(room.getSeatQuantity(), request.getSeatColumn(), request.getSeatRow());

        seat.setRoom(room);

        Seat secondSeat = null;
        
        if (seat.getSeatType() == SeatType.COUPLE) {
            SeatValidator.validateCoupleSeatPosition(request.getSeatColumn(), room.getSeatQuantity());
            
            secondSeat = seatMapper.mapSecondSeat(seat);
        }
        
        Seat savedSeat = seatRepository.save(seat);

        if (secondSeat != null) {
            secondSeat.setLinkedSeat(savedSeat.getId());
            seatRepository.save(secondSeat);
        }

        return seatMapper.toDto(savedSeat);
    }

}
