package com.mms.mms_api.business.handler.schedule;

import com.mms.mms_api.business.query.schedule.ScheduleGetAllQuery;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.schedule.ScheduleDto;
import com.mms.mms_api.util.mapper.ScheduleMapper;

import java.util.List;

public class ScheduleGetAllHandler extends ScheduleBaseHandler<ScheduleGetAllQuery, List<ScheduleDto>> {

    public ScheduleGetAllHandler(ScheduleGetAllQuery request, ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository) {
        super(request, scheduleMapper, scheduleRepository);
    }

    @Override
    public List<ScheduleDto> execute() {
        return scheduleRepository.findAll().stream().map(scheduleMapper::toDto).toList();
    }
}
