package com.mms.mms_api.business.handler.seat;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.command.seat.SeatDeleteCommand;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.SeatMapper;

public class SeatDeleteHandler extends SeatBaseHandler<SeatDeleteCommand, Void> {
    private final ScheduleSeatRepository scheduleSeatRepository;

    public SeatDeleteHandler(SeatDeleteCommand request, SeatMapper seatMapper, SeatRepository seatRepository,
            ScheduleSeatRepository scheduleSeatRepository) {
        super(request, seatMapper, seatRepository);
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public Void execute() {
        Seat seat = seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));

        List<ScheduleSeat> scheduleSeats = seat.getScheduleSeats();
        if (!CollectionUtils.isEmpty(scheduleSeats) && !scheduleSeats.contains(null)) {
            scheduleSeatRepository.deleteAll(scheduleSeats);
        }

        Seat linkedSeat = seatRepository.findFirstByLinkedSeat(seat);

        if (linkedSeat != null) {
            seatRepository.delete(linkedSeat);
        }

        seatRepository.delete(seat);

        return null;
    }
}