package com.mms.mms_api.business.handler.seat;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.seat.SeatUpdateCommand;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.dto.seat.SeatDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.SeatType;
import com.mms.mms_api.util.mapper.SeatMapper;

/**
 * Handles seat update commands.
 */
@Component
public class SeatUpdateHandler extends SeatBaseHandler<SeatUpdateCommand, SeatDto> {
    /**
     * Creates a SeatUpdateHandler.
     *
     * @param seatMapper seat mapper
     * @param seatRepository seat repository
     */
    public SeatUpdateHandler(SeatMapper seatMapper, SeatRepository seatRepository) {
        super(seatMapper, seatRepository);
    }

    /**
     * Updates a seat's properties and manages couple-seat linkage changes.
     *
     * @param request seat update command
     * @return updated seat DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the seat does not exist
     */
    @Override
    public SeatDto execute(SeatUpdateCommand request) {
        Seat seat = seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        Room room = seat.getRoom();

        Seat secondSeat = seatRepository.findFirstByLinkedSeat(seat);

        SeatType oldSeatType = seat.getSeatType();
        SeatType newSeatType = request.getSeatType();

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