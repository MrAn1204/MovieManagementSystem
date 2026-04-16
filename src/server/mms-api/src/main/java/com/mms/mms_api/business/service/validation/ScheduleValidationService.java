package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.model.Schedule;

import lombok.AllArgsConstructor;

/**
 * Validation helper for schedule existence and lookup.
 */
@Service
@AllArgsConstructor
public class ScheduleValidationService {
    private final ScheduleRepository scheduleRepository;

    /**
     * Checks whether a schedule exists by id.
     *
     * @param id schedule identifier
     * @return true when the schedule exists
     */
    public boolean existsById(@NonNull UUID id) {
        return scheduleRepository.existsById(id);
    }

    /**
     * Returns a schedule by id.
     *
     * @param id schedule identifier
     * @return resolved schedule or null
     */
    public Schedule getById(@NonNull UUID id) {
        return scheduleRepository.findById(id).orElse(null);
    }
}
