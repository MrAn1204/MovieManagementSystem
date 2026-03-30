package com.mms.mms_api.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mms.mms_api.model.Schedule;
import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;
import com.mms.mms_api.model.Seat;

public interface ScheduleSeatRepository extends JpaRepository<ScheduleSeat, ScheduleSeatId> {
    List<ScheduleSeat> findByScheduleAndSeatIn(Schedule schedule, List<Seat> seats);
}
