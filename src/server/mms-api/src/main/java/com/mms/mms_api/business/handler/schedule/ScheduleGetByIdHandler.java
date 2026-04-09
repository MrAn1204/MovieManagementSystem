package com.mms.mms_api.business.handler.schedule;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.schedule.ScheduleGetByIdQuery;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.schedule.ScheduleDetailDto;
import com.mms.mms_api.util.mapper.ScheduleMapper;
import com.mms.mms_api.exception.ResourceNotFoundException;

@Component
public class ScheduleGetByIdHandler extends ScheduleBaseHandler<ScheduleGetByIdQuery, ScheduleDetailDto> {

    public ScheduleGetByIdHandler(ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository) {
        super(scheduleMapper, scheduleRepository);
    }

    @Override
    public ScheduleDetailDto execute(ScheduleGetByIdQuery request) {
        return scheduleRepository.findById(request.getId())
                .map(scheduleMapper::toDetailDto)
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));
    }
}
