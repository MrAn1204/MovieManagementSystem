package com.mms.mms_api.business.handler.seat;

import java.util.List;

import com.mms.mms_api.business.query.seat.SeatGetAllWithStatusQuery;
import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.dto.SeatStatusDto;

public class SeatGetAllWithStatusHandler extends SeatBaseHandler<SeatGetAllWithStatusQuery, List<SeatStatusDto>>  {
    private final ScheduleSeatRepository scheduleSeatRepository;

    public SeatGetAllWithStatusHandler(SeatGetAllWithStatusQuery request, ScheduleSeatRepository scheduleSeatRepository) {
        super(request, null, null);
        this.scheduleSeatRepository = scheduleSeatRepository;
    }

    @Override
    public List<SeatStatusDto> execute() {
        return scheduleSeatRepository.findAllByScheduleId(request.getScheduleId());
    }

}
