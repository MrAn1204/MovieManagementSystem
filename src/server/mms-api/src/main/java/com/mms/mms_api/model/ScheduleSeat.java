package com.mms.mms_api.model;

import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Join entity mapping seat reservation state within a schedule.
 */
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ScheduleSeat {
    @EmbeddedId
    @NonNull
    private ScheduleSeatId id;

    @Column(nullable = false)
    private boolean reserved = false;

    @NonNull
    @ManyToOne
    @MapsId("scheduleId")
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @NonNull
    @ManyToOne
    @MapsId("seatId")
    @JoinColumn(name = "seat_id")
    private Seat seat;
}
