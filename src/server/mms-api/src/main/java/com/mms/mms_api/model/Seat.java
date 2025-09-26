package com.mms.mms_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Seat extends BaseEntity {
    @Column(nullable = false)
    private int seatColumn;

    @Column(nullable = false)
    private int seatRow;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "seat")
    private List<ScheduleSeat> scheduleSeats;

    private UUID linkedSeat = null;
}
