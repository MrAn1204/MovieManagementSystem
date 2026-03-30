package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.ScheduleSeatRepository;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;

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

    public List<ScheduleSeat> getByScheduleAndSeatIn(Schedule schedule, List<Seat> seats) {
        return scheduleSeatRepository.findByScheduleAndSeatIn(schedule, seats);
    }
}
