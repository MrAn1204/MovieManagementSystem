package com.mms.mms_api.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mms.mms_api.model.Room;

/**
 * Repository for room persistence and specification queries.
 */
public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, UUID id);
}
