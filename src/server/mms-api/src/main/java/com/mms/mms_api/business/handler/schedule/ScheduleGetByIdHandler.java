package com.mms.mms_api.business.handler.schedule;

import com.mms.mms_api.business.query.schedule.ScheduleGetByIdQuery;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.ScheduleDetailDto;
import com.mms.mms_api.util.mapper.ScheduleMapper;
import com.mms.mms_api.exception.ResourceNotFoundException;

public class ScheduleGetByIdHandler extends ScheduleBaseHandler<ScheduleGetByIdQuery, ScheduleDetailDto> {

    public ScheduleGetByIdHandler(ScheduleGetByIdQuery request, ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository) {
        super(request, scheduleMapper, scheduleRepository);
    }

    @Override
    public ScheduleDetailDto execute() {
        return scheduleRepository.findById(request.getId())
                .map(scheduleMapper::toDetailDto)
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));
    }
}
