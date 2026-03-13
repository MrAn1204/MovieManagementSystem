package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.RoomRepository;
import com.mms.mms_api.model.Room;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoomValidationService {
    private final RoomRepository roomRepository;

    public boolean existsById(@NonNull UUID id) {
        return roomRepository.existsById(id);
    }
    
    public Room getById(@NonNull UUID id) {
        return roomRepository.findById(id).orElse(null);
    }

    public boolean existsByName(String name) {
        return roomRepository.existsByName(name);
    }

    public boolean existsByNameAndIdNot(String name, UUID id) {
        return roomRepository.existsByNameAndIdNot(name, id);
    }
}
