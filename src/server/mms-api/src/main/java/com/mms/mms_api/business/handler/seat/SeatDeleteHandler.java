package com.mms.mms_api.business.handler.seat;

import com.mms.mms_api.business.command.seat.SeatDeleteCommand;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.util.mapper.SeatMapper;

public class SeatDeleteHandler extends SeatBaseHandler<SeatDeleteCommand, Void> {
    public SeatDeleteHandler(SeatDeleteCommand request, SeatMapper seatMapper, SeatRepository seatRepository) {
        super(request, seatMapper, seatRepository);
    }

    @Override
    public Void execute() {
        seatRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.SEAT_NOT_FOUND));

        seatRepository.deleteById(request.getId());
        return null;
    }
}