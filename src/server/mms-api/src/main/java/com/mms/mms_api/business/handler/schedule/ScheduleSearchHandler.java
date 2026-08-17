package com.mms.mms_api.business.handler.schedule;

import org.springframework.stereotype.Component;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import com.mms.mms_api.business.query.schedule.ScheduleSearchQuery;
import com.mms.mms_api.business.specification.ScheduleSpecification;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.dto.schedule.ScheduleDto;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.util.SearchHelper;
import com.mms.mms_api.util.mapper.ScheduleMapper;

/**
 * Handles schedule search queries.
 */
@Component
public class ScheduleSearchHandler extends ScheduleBaseHandler<ScheduleSearchQuery, PaginatedResult<ScheduleDto>> {
    /**
     * Creates a ScheduleSearchHandler.
     *
     * @param scheduleMapper schedule mapper
     * @param scheduleRepository schedule repository
     */
    public ScheduleSearchHandler(ScheduleMapper scheduleMapper,
            ScheduleRepository scheduleRepository) {
        super(scheduleMapper, scheduleRepository);
    }

    /**
     * Executes a paginated schedule search using the supplied filters.
     *
     * @param request search query with filters and pagination parameters
     * @return paginated result of schedule DTOs
     */
    @Override
    public PaginatedResult<ScheduleDto> execute(ScheduleSearchQuery request) {
        Specification<Schedule> spec = new ScheduleSpecification(request);

        Page<Schedule> schedules = SearchHelper.getPage(request.getPageNumber(), request.getPageSize(),
                pageable -> scheduleRepository.findAll(spec, pageable));

        return SearchHelper.getResult(schedules, scheduleMapper::toDto);
    }
}
