package com.mms.mms_api.business.handler.schedule;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.schedule.ScheduleGetAllQuery;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.schedule.ScheduleDto;
import com.mms.mms_api.util.mapper.ScheduleMapper;

import java.util.List;

/**
 * Handles requests to retrieve all schedules.
 */
@Component
public class ScheduleGetAllHandler extends ScheduleBaseHandler<ScheduleGetAllQuery, List<ScheduleDto>> {

    /**
     * Creates a ScheduleGetAllHandler.
     *
     * @param scheduleMapper schedule mapper
     * @param scheduleRepository schedule repository
     */
    public ScheduleGetAllHandler(ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository) {
        super(scheduleMapper, scheduleRepository);
    }

    /**
     * Retrieves all schedules.
     *
     * @param request query object
     * @return list of schedule DTOs
     */
    @Override
    public List<ScheduleDto> execute(ScheduleGetAllQuery request) {
        return scheduleRepository.findAll().stream().map(scheduleMapper::toDto).toList();
    }
}
