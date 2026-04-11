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

/**
 * Handles seat creation commands.
 */
@Component
public class SeatCreateHandler extends SeatBaseHandler<SeatCreateCommand, SeatDto> {
    private final RoomRepository roomRepository;

    /**
     * Creates a SeatCreateHandler.
     *
     * @param seatMapper seat mapper
     * @param seatRepository seat repository
     * @param roomRepository room repository
     */
    public SeatCreateHandler(SeatMapper seatMapper, SeatRepository seatRepository,
            RoomRepository roomRepository) {
        super(seatMapper, seatRepository);
        this.roomRepository = roomRepository;
    }

    /**
     * Creates a seat in the given room; for a COUPLE type, automatically creates and links the adjacent seat.
     *
     * @param request seat create command
     * @return created seat DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the room does not exist
     */
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
