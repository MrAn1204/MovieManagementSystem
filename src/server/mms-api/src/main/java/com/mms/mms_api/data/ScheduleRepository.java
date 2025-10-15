package com.mms.mms_api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mms.mms_api.model.Schedule;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID>, JpaSpecificationExecutor<Schedule> {

}