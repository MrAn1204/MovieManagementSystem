package com.mms.mms_api.data;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mms.mms_api.dto.SeatStatusDto;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;

public interface ScheduleSeatRepository extends JpaRepository<ScheduleSeat, ScheduleSeatId> {
    @Query("SELECT new com.mms.mms_api.dto.SeatStatusDto(ss.seat.id, ss.seat.seatType, ss.reserved) FROM ScheduleSeat ss WHERE ss.id.scheduleId = :scheduleId")
    List<SeatStatusDto> findAllByScheduleId(UUID scheduleId);
}
