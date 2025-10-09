package com.mms.mms_api.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mms.mms_api.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
    List<Seat> findByLinkedSeatIsNull();

    Seat findFirstByLinkedSeat(Seat linkedSeat);
}
