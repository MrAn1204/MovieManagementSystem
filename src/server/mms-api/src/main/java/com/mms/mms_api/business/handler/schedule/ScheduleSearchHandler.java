package com.mms.mms_api.business.handler.schedule;

import org.springframework.stereotype.Component;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.schedule.ScheduleSearchQuery;
import com.mms.mms_api.business.specification.ScheduleSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.schedule.ScheduleDto;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.ScheduleMapper;

@Component
public class ScheduleSearchHandler extends ScheduleBaseHandler<ScheduleSearchQuery, PaginatedResult<ScheduleDto>> {
    public ScheduleSearchHandler(ScheduleMapper scheduleMapper,
            ScheduleRepository scheduleRepository) {
        super(scheduleMapper, scheduleRepository);
    }

    @Override
    public PaginatedResult<ScheduleDto> execute(ScheduleSearchQuery request) {
        Pageable pageable = SearchHelper.generatePageable(request.getPageNumber(), request.getPageSize());

        Specification<Schedule> spec = new ScheduleSpecification(request);

        Page<Schedule> schedules = scheduleRepository.findAll(spec, pageable);

        return SearchHelper.generatePaginatedResult(schedules, scheduleMapper::toDto);
    }
}
