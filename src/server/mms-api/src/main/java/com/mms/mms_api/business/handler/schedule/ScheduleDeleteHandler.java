package com.mms.mms_api.business.handler.schedule;

import org.springframework.transaction.annotation.Transactional;

import com.mms.mms_api.business.command.schedule.ScheduleDeleteCommand;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;

public class ScheduleDeleteHandler extends ScheduleBaseHandler<ScheduleDeleteCommand, Void> {

    public ScheduleDeleteHandler(ScheduleDeleteCommand request, ScheduleRepository scheduleRepository) {
        super(request, null, scheduleRepository);
    }

    @Override
    @Transactional
    public Void execute() {
        scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));

        scheduleRepository.deleteById(request.getId());

        return null;
    }
}
