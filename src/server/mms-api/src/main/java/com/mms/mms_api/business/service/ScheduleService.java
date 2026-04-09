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

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleService {
    private final RequestMediator mediator;

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ScheduleDetailDto handle(ScheduleCreateCommand request) {
        return mediator.execute(request);
    }

    public List<ScheduleDto> handle(ScheduleGetAllQuery request) {
        return mediator.execute(request);
    }

    public ScheduleDetailDto handle(ScheduleGetByIdQuery request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public ScheduleDetailDto handle(ScheduleUpdateCommand request) {
        return mediator.execute(request);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Transactional
    public void handle(ScheduleDeleteCommand request) {
        mediator.execute(request);
    }

    public PaginatedResult<ScheduleDto> handle(ScheduleSearchQuery request) {
        return mediator.execute(request);
    }
}
