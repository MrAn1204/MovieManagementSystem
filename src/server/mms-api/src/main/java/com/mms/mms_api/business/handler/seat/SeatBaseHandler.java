package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.SeatType;
import com.mms.mms_api.util.mapper.SeatMapper;
import com.mms.mms_api.util.validator.SeatValidator;

public abstract class SeatBaseHandler<I, O> extends BaseHandler<I, O> {
    protected final SeatRepository seatRepository;

    protected final SeatMapper seatMapper;

    protected SeatBaseHandler(I request, SeatMapper seatMapper, SeatRepository seatRepository) {
        super(request);
        this.seatMapper = seatMapper;
        this.seatRepository = seatRepository;
    }

    protected Seat linkCoupleSeat(Seat seat, Room room) {
        Seat secondSeat;
        SeatValidator.validateCoupleSeatPosition(seat.getSeatColumn(), room.getSeatQuantity());

        secondSeat = room.getSeats().stream()
                .filter(s -> s.getSeatColumn() == seat.getSeatColumn() + 1
                        && s.getSeatRow() == seat.getSeatRow())
                .findFirst()
                .orElse(null);

        if (secondSeat == null || secondSeat.getSeatType() != SeatType.COUPLE) {
            secondSeat = seatMapper.mapLinkedSeat(seat, secondSeat);
        } else {
            throw new InvalidInputException("seat.position.invalid");
        }

        return secondSeat;
    }
}