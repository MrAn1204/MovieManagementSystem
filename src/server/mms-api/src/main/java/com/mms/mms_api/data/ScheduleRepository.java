package com.mms.mms_api.data;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mms.mms_api.data.projection.TodayScheduleProjection;
import com.mms.mms_api.model.Schedule;

import java.util.List;
import java.util.UUID;

/**
 * Repository for schedule persistence and specification queries.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, UUID>, JpaSpecificationExecutor<Schedule> {
    @Query("""
            SELECT s.id AS id, s.movie.name AS movieName, s.showTime AS showTime, s.room.name AS roomName
            FROM Schedule s
            WHERE s.showTime >= :start AND s.showTime < :end
            ORDER BY s.showTime ASC, s.movie.name ASC
            """)
    List<TodayScheduleProjection> findUpcomingSchedules(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}