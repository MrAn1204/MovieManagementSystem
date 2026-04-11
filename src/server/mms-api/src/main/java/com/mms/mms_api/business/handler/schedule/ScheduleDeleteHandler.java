package com.mms.mms_api.business.handler.schedule;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.schedule.ScheduleDeleteCommand;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;

/**
 * Handles schedule delete commands.
 */
@Component
public class ScheduleDeleteHandler extends ScheduleBaseHandler<ScheduleDeleteCommand, Void> {

    /**
     * Creates a ScheduleDeleteHandler.
     *
     * @param scheduleRepository schedule repository
     */
    public ScheduleDeleteHandler(ScheduleRepository scheduleRepository) {
        super(null, scheduleRepository);
    }

    /**
     * Deletes a schedule by its identifier.
     *
     * @param request delete command containing the target schedule id
     * @return {@code null}
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the schedule does not exist
     */
    @Override
    
    public Void execute(ScheduleDeleteCommand request) {
        scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));

        scheduleRepository.deleteById(request.getId());

        return null;
    }
}
