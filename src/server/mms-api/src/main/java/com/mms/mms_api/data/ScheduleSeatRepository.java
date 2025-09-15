package com.mms.mms_api.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mms.mms_api.model.ScheduleSeat;
import com.mms.mms_api.model.ScheduleSeatId;

public interface ScheduleSeatRepository extends JpaRepository<ScheduleSeat, ScheduleSeatId> {

}
