package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.SeatType;
import com.mms.mms_api.util.mapper.SeatMapper;

/**
 * Base handler for seat-related requests.
 */
public abstract class SeatBaseHandler<I, O> extends BaseHandler<I, O> {
    protected final SeatRepository seatRepository;

    protected final SeatMapper seatMapper;

    /**
     * Creates a seat base handler.
     *
     * @param seatMapper seat mapper
     * @param seatRepository seat repository
     */
    protected SeatBaseHandler(SeatMapper seatMapper, SeatRepository seatRepository) {
        this.seatMapper = seatMapper;
        this.seatRepository = seatRepository;
    }

    /**
     * Links a couple seat to the adjacent seat in the same row.
     *
     * @param seat primary seat to link
     * @param room room containing the seat grid
     * @return the linked secondary seat
     */
    protected Seat linkCoupleSeat(Seat seat, Room room) {
        Seat secondSeat;

        secondSeat = room.getSeatAt(seat.getSeatRow(), seat.getSeatColumn() + 1);

        if (secondSeat == null || secondSeat.getSeatType() != SeatType.COUPLE) {
            secondSeat = seatMapper.mapLinkedSeat(seat, secondSeat);
        } else {
            throw new InvalidInputException("seat.position.invalid");
        }

        return secondSeat;
    }
}