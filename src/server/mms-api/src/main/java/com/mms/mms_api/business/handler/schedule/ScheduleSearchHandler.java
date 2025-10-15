package com.mms.mms_api.business.handler.schedule;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.schedule.ScheduleSearchQuery;
import com.mms.mms_api.business.specification.ScheduleSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.ScheduleDto;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.util.mapper.ScheduleMapper;

public class ScheduleSearchHandler extends ScheduleBaseHandler<ScheduleSearchQuery, PaginatedResult<ScheduleDto>> {
    public ScheduleSearchHandler(ScheduleSearchQuery request, ScheduleMapper scheduleMapper,
            ScheduleRepository scheduleRepository) {
        super(request, scheduleMapper, scheduleRepository);
    }

    @Override
    public PaginatedResult<ScheduleDto> execute() {
        Sort.Direction sortDirection = Sort.Direction.fromString(request.getSortDirection().name());

        String sortBy = request.getSortBy();

        Pageable pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize(),
                Sort.by(sortDirection, sortBy));

        Specification<Schedule> spec = new ScheduleSpecification(request);

        Page<Schedule> schedules = scheduleRepository.findAll(spec, pageable);

        List<ScheduleDto> scheduleDtos = schedules.getContent().stream()
                .map(scheduleMapper::toDto)
                .toList();

        return new PaginatedResult<>(scheduleDtos, schedules.getTotalElements(), schedules.getTotalPages(),
                request.getPageSize(), request.getPageNumber());
    }
}
