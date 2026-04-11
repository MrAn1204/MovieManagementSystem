package com.mms.mms_api.business.handler.seat;

import org.springframework.stereotype.Component;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.mms.mms_api.business.command.seat.SeatDeleteCommand;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.util.mapper.SeatMapper;

/**
 * Handles seat delete commands.
 */
@Component
public class SeatDeleteHandler extends SeatBaseHandler<SeatDeleteCommand, Void> {
    private final ScheduleSeatRepository scheduleSeatRepository;

    /**
     * Creates a SeatDeleteHandler.
     *
     * @param seatMapper seat mapper
     * @param seatRepository seat repository
     * @param scheduleSeatRepository schedule-seat repository
     */
    public SeatDeleteHandler(SeatMapper seatMapper, SeatRepository seatRepository,
            ScheduleSeatRepository scheduleSeatRepository) {
        super(seatMapper, seatRepository);
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    /**
     * Deletes a seat, its schedule-seat entries, and the linked couple seat when present.
     *
     * @param request delete command containing the target seat id
     * @return {@code null}
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the seat does not exist
     */
    @Override
    public Void execute(SeatDeleteCommand request) {
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