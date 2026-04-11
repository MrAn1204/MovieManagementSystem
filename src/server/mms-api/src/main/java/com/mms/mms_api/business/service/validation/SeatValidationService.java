package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.SeatRepository;
import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;

import lombok.AllArgsConstructor;

/**
 * Validation helper for seat existence and lookup operations.
 */
@Service
@AllArgsConstructor
public class SeatValidationService {
    private final SeatRepository seatRepository;

    /**
     * Checks whether a seat exists by id.
     *
     * @param id seat identifier
     * @return true when the seat exists
     */
    public boolean existsById(@NonNull UUID id) {
        return seatRepository.existsById(id);
    }

    /**
     * Returns a seat by id.
     *
     * @param id seat identifier
     * @return resolved seat or null
     */
    public Seat getById(@NonNull UUID id) {
        return seatRepository.findById(id).orElse(null);
    }

    /**
     * Checks whether a room has any seats.
     *
     * @param room room entity
     * @return true when seats exist for the room
     */
    public boolean existsByRoom(Room room) {
        return seatRepository.existsByRoom(room);
    }

    /**
     * Returns seats by id list.
     *
     * @param ids seat identifiers
     * @return matching seats
     */
    public List<Seat> getByIdIn(List<UUID> ids) {
        return seatRepository.findByIdIn(ids);
    }
}
