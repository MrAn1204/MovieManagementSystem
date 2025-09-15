package com.mms.mms_api.business.handler.schedule;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.util.mapper.ScheduleMapper;

public abstract class ScheduleBaseHandler<I, O> extends BaseHandler<I, O> {
    protected final ScheduleMapper scheduleMapper;

    protected final ScheduleRepository scheduleRepository;

    protected ScheduleBaseHandler(I request, ScheduleMapper scheduleMapper, ScheduleRepository scheduleRepository) {
        super(request);
        this.scheduleMapper = scheduleMapper;
        this.scheduleRepository = scheduleRepository;
    }
}
