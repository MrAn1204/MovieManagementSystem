package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduleSeatValidationService {
    private final ScheduleSeatRepository scheduleSeatRepository;

    public boolean existsById(@NonNull ScheduleSeatId id) {
        return scheduleSeatRepository.existsById(id);
    }

    public ScheduleSeat getById(UUID scheduleId, UUID seatId) {
        if (scheduleId == null || seatId == null) {
            return null;
        }

        return scheduleSeatRepository.findById(new ScheduleSeatId(scheduleId, seatId)).orElse(null);
    }


}
