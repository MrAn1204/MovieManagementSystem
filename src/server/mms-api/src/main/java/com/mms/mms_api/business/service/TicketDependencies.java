package com.mms.mms_api.business.service;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.mms.mms_api.data.PromotionRepository;
import com.mms.mms_api.data.ScheduleRepository;
import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.data.UserRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Promotion;
import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.Seat;
import com.mms.mms_api.model.User;

import lombok.AllArgsConstructor;

/**
 * Resolves and validates ticket-related domain dependencies from repositories.
 */
@Component
@AllArgsConstructor
public class TicketDependencies {
    private final ScheduleRepository scheduleRepository;

    private final SeatRepository seatRepository;

    private final PromotionRepository promotionRepository;

    private final UserRepository userRepository;

    /**
     * Returns a schedule by id.
     *
     * @param scheduleId schedule identifier
     * @return resolved schedule
     */
    public Schedule getScheduleById(@NonNull UUID scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("schedule.notFound"));
    }

    /**
     * Returns a seat by id.
     *
     * @param seatId seat identifier
     * @return resolved seat
     */
    public Seat getSeatById(@NonNull UUID seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new ResourceNotFoundException("seat.notFound"));
    }

    /**
     * Returns a promotion by id when provided.
     *
     * @param promotionId promotion identifier, nullable
     * @return resolved promotion or null when no id is provided
     */
    public Promotion getPromotionById(UUID promotionId) {
        return (promotionId != null)
                ? promotionRepository.findById(promotionId)
                        .orElseThrow(() -> new ResourceNotFoundException("promotion.notFound"))
                : null;
    }

    /**
     * Returns a user by id.
     *
     * @param userId user identifier
     * @return resolved user
     */
    public User getUserById(@NonNull UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user.notFound"));
    }

    /**
     * Returns all seats by ids and validates completeness.
     *
     * @param seatIds seat identifiers
     * @return resolved seat list
     */
    public List<Seat> getSeatByIdIn(List<UUID> seatIds) {
        List<Seat> seats = seatRepository.findByIdIn(seatIds);
        if (seats == null || seats.size() != seatIds.size()) {
            throw new ResourceNotFoundException("ticket.seats.invalid");
        }
        return seats;
    }
}
