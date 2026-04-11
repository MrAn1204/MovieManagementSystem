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

/**
 * Validation helper for schedule-seat association checks.
 */
@Service
@AllArgsConstructor
public class ScheduleSeatValidationService {
    private final ScheduleSeatRepository scheduleSeatRepository;

    /**
     * Checks whether a schedule-seat association exists.
     *
     * @param id schedule-seat composite identifier
     * @return true when the association exists
     */
    public boolean existsById(@NonNull ScheduleSeatId id) {
        return scheduleSeatRepository.existsById(id);
    }

    /**
     * Returns a schedule-seat association by schedule and seat id.
     *
     * @param scheduleId schedule identifier
     * @param seatId seat identifier
     * @return association or null when unavailable
     */
    public ScheduleSeat getById(UUID scheduleId, UUID seatId) {
        if (scheduleId == null || seatId == null) {
            return null;
        }

        return scheduleSeatRepository.findById(new ScheduleSeatId(scheduleId, seatId)).orElse(null);
    }

    /**
     * Returns schedule-seat associations for a schedule and seat set.
     *
     * @param schedule schedule entity
     * @param seats seat entities
     * @return matching schedule-seat records
     */
    public List<ScheduleSeat> getByScheduleAndSeatIn(Schedule schedule, List<Seat> seats) {
        return scheduleSeatRepository.findByScheduleAndSeatIn(schedule, seats);
    }
}
