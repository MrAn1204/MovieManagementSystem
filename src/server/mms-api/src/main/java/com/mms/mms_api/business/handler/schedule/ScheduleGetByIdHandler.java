package com.mms.mms_api.business.handler.schedule;

import com.mms.mms_api.util.CurrentUserHelper;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.schedule.ScheduleGetByIdQuery;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.schedule.ScheduleDetailDto;
import com.mms.mms_api.util.mapper.ScheduleMapper;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Schedule;

/**
 * Handles requests to retrieve schedule by id.
 */
@Component
public class ScheduleGetByIdHandler extends ScheduleBaseHandler<ScheduleGetByIdQuery, ScheduleDetailDto> {

    private final CurrentUserHelper currentUser;

    /**
     * Creates a ScheduleGetByIdHandler.
     *
     * @param scheduleMapper schedule mapper
     * @param scheduleRepository schedule repository
     * @param currentUserHelper current user helper
     */
    public ScheduleGetByIdHandler(ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository, CurrentUserHelper currentUserHelper) {
        super(scheduleMapper, scheduleRepository);
        this.currentUser = currentUserHelper;
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
        Schedule schedule = scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));

        ScheduleDetailDto dto = scheduleMapper.toDetailDto(schedule);

        if (currentUser.isAdmin()) {
            dto.setAudit(new AuditDto());
        }

        return dto;
    }
}
