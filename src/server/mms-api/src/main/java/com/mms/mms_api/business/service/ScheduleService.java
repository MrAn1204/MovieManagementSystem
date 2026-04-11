package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.schedule.ScheduleCreateCommand;
import com.mms.mms_api.business.command.schedule.ScheduleDeleteCommand;
import com.mms.mms_api.business.command.schedule.ScheduleUpdateCommand;
import com.mms.mms_api.business.query.schedule.ScheduleGetAllQuery;
import com.mms.mms_api.business.query.schedule.ScheduleGetByIdQuery;
import com.mms.mms_api.business.query.schedule.ScheduleSearchQuery;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.schedule.ScheduleDetailDto;
import com.mms.mms_api.dto.schedule.ScheduleDto;
import com.mms.mms_api.mediator.RequestMediator;
import com.mms.mms_api.util.validator.ScheduleValidator;

import lombok.AllArgsConstructor;

/**
 * Provides CRUD and search operations for schedules.
 */
@Service
@AllArgsConstructor
public class ScheduleService {
    private final RequestMediator mediator;
    private final ScheduleValidator scheduleValidator;

    /**
     * Creates a schedule.
     *
     * @param request create command
     * @return created schedule details
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ScheduleDetailDto handle(ScheduleCreateCommand request) {
        scheduleValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Returns all schedules.
     *
     * @param request get-all query
     * @return list of schedule DTOs
     */
    public List<ScheduleDto> handle(ScheduleGetAllQuery request) {
        return mediator.execute(request);
    }

    /**
     * Returns schedule details by id.
     *
     * @param request get-by-id query
     * @return schedule detail DTO
     */
    public ScheduleDetailDto handle(ScheduleGetByIdQuery request) {
        return mediator.execute(request);
    }

    /**
     * Updates a schedule.
     *
     * @param request update command
     * @return updated schedule details
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ScheduleDetailDto handle(ScheduleUpdateCommand request) {
        scheduleValidator.validate(request);
        return mediator.execute(request);
    }

    /**
     * Deletes a schedule.
     *
     * @param request delete command
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(ScheduleDeleteCommand request) {
        mediator.execute(request);
    }

    /**
     * Searches schedules with pagination.
     *
     * @param request search query
     * @return paginated schedule result
     */
    public PaginatedResult<ScheduleDto> handle(ScheduleSearchQuery request) {
        return mediator.execute(request);
    }
}
