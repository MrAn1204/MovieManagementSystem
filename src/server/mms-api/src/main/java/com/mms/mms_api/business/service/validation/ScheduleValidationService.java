package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.model.Schedule;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleValidationService {
    private final ScheduleRepository scheduleRepository;

    public boolean existsById(@NonNull UUID id) {
        return scheduleRepository.existsById(id);
    }

    public Schedule getById(@NonNull UUID id) {
        return scheduleRepository.findById(id).orElse(null);
    }
}
