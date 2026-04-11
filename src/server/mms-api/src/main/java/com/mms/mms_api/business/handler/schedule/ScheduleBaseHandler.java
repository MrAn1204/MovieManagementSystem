package com.mms.mms_api.business.handler.schedule;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.util.mapper.ScheduleMapper;

/**
 * Base handler for schedule-related requests.
 */
public abstract class ScheduleBaseHandler<I, O> extends BaseHandler<I, O> {
    protected final ScheduleMapper scheduleMapper;

    protected final ScheduleRepository scheduleRepository;

    /**
     * Creates a schedule base handler.
     *
     * @param scheduleMapper schedule mapper
     * @param scheduleRepository schedule repository
     */
    protected ScheduleBaseHandler(ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository) {
        this.scheduleMapper = scheduleMapper;
        this.scheduleRepository = scheduleRepository;
    }
}
