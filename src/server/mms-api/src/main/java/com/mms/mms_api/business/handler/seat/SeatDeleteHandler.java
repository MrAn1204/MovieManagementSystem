package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.command.seat.SeatDeleteCommand;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.SeatMapper;

public class SeatDeleteHandler extends SeatBaseHandler<SeatDeleteCommand, Void> {
    private final ScheduleSeatRepository scheduleSeatRepository;

    public SeatDeleteHandler(SeatDeleteCommand request, SeatMapper seatMapper, SeatRepository seatRepository, ScheduleSeatRepository scheduleSeatRepository) {
        super(request, seatMapper, seatRepository);
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public Void execute() {
        Seat seat = seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.SEAT_NOT_FOUND));

        scheduleSeatRepository.deleteAll(seat.getScheduleSeats());

        seatRepository.delete(seat);
        return null;
    }
}