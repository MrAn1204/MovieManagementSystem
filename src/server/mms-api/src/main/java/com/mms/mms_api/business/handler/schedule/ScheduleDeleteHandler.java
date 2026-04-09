package com.mms.mms_api.business.handler.schedule;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.schedule.ScheduleDeleteCommand;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;

@Component
public class ScheduleDeleteHandler extends ScheduleBaseHandler<ScheduleDeleteCommand, Void> {

    public ScheduleDeleteHandler(ScheduleRepository scheduleRepository) {
        super(null, scheduleRepository);
    }

    @Override
    
    public Void execute(ScheduleDeleteCommand request) {
        scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));

        scheduleRepository.deleteById(request.getId());

        return null;
    }
}
