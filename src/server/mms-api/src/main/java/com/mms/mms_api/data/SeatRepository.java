package com.mms.mms_api.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mms.mms_api.model.Room;
import com.mms.mms_api.model.Seat;

/**
 * Repository for seat persistence and lookup operations.
 */
public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findByLinkedSeatIsNull();

    Seat findFirstByLinkedSeat(Seat linkedSeat);

    boolean existsByRoom(Room room);

    List<Seat> findByRoomId(UUID roomId);

    List<Seat> findByIdIn(List<UUID> ids);
}
