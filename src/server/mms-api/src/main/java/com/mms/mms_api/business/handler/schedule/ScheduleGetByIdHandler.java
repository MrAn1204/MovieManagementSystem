package com.mms.mms_api.business.handler.schedule;

import com.mms.mms_api.business.query.schedule.ScheduleGetByIdQuery;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.ScheduleDto;
import com.mms.mms_api.util.mapper.ScheduleMapper;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.exception.ErrorMessage;

public class ScheduleGetByIdHandler extends ScheduleBaseHandler<ScheduleGetByIdQuery, ScheduleDto> {

    public ScheduleGetByIdHandler(ScheduleGetByIdQuery request, ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository) {
        super(request, scheduleMapper, scheduleRepository);
    }

    @Override
    public ScheduleDto execute() {
        return scheduleRepository.findById(request.getId())
                .map(scheduleMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.SCHEDULE_NOT_FOUND));
    }
}
