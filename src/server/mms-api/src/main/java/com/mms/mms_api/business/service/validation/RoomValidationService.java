package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.model.Room;

import lombok.AllArgsConstructor;

/**
 * Validation helper for room lookups and uniqueness checks.
 */
@Service
@AllArgsConstructor
public class RoomValidationService {
    private final RoomRepository roomRepository;

    /**
     * Checks whether a room exists by id.
     *
     * @param id room identifier
     * @return true when the room exists
     */
    public boolean existsById(@NonNull UUID id) {
        return roomRepository.existsById(id);
    }
    
    /**
     * Returns a room by id.
     *
     * @param id room identifier
     * @return resolved room or null
     */
    public Room getById(@NonNull UUID id) {
        return roomRepository.findById(id).orElse(null);
    }

    /**
     * Checks whether a room name already exists.
     *
     * @param name room name
     * @return true when the name exists
     */
    public boolean existsByName(String name) {
        return roomRepository.existsByName(name);
    }

    /**
     * Checks whether a room name exists in another room.
     *
     * @param name room name
     * @param id excluded room identifier
     * @return true when the name exists in a different room
     */
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return roomRepository.existsByNameAndIdNot(name, id);
    }
}
