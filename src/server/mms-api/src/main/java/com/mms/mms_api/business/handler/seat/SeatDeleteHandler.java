package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.command.seat.SeatDeleteCommand;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.SeatMapper;

import jakarta.transaction.Transactional;

public class SeatDeleteHandler extends SeatBaseHandler<SeatDeleteCommand, Void> {
    private final ScheduleSeatRepository scheduleSeatRepository;

    public SeatDeleteHandler(SeatDeleteCommand request, SeatMapper seatMapper, SeatRepository seatRepository,
            ScheduleSeatRepository scheduleSeatRepository) {
        super(request, seatMapper, seatRepository);
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    @Transactional
    public Void execute() {
        Seat seat = seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        scheduleSeatRepository.deleteAll(seat.getScheduleSeats());

        Seat linkedSeat = seatRepository.findFirstByLinkedSeat(seat);

        if (linkedSeat != null) {
            seatRepository.delete(linkedSeat);
        }

        seatRepository.delete(seat);

        return null;
    }
}