package com.mms.mms_api.business.handler.schedule;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.schedule.ScheduleGetByIdQuery;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.schedule.ScheduleDetailDto;
import com.mms.mms_api.util.mapper.ScheduleMapper;
import com.mms.mms_api.exception.ResourceNotFoundException;

/**
 * Handles requests to retrieve schedule by id.
 */
@Component
public class ScheduleGetByIdHandler extends ScheduleBaseHandler<ScheduleGetByIdQuery, ScheduleDetailDto> {

    /**
     * Creates a ScheduleGetByIdHandler.
     *
     * @param scheduleMapper schedule mapper
     * @param scheduleRepository schedule repository
     */
    public ScheduleGetByIdHandler(ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository) {
        super(scheduleMapper, scheduleRepository);
    }

    /**
     * Retrieves a schedule by its identifier.
     *
     * @param request query containing the target schedule id
     * @return schedule detail DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the schedule does not exist
     */
    @Override
    public ScheduleDetailDto execute(ScheduleGetByIdQuery request) {
        return scheduleRepository.findById(request.getId())
                .map(scheduleMapper::toDetailDto)
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));
    }
}
