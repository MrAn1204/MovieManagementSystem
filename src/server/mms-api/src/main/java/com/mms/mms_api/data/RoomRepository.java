package com.mms.mms_api.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Room;

public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {
    default boolean isRoomFull(@NonNull UUID id) {
        return findById(id)
                .map(room -> room.getSeats().size() >= room.getSeatQuantity())
                .orElseThrow(() -> new ResourceNotFoundException("room.notFound"));
    }
}
