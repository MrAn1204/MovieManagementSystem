package com.mms.mms_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Entity representing a physical seat in a room.
 */
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
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "seat")
    private List<ScheduleSeat> scheduleSeats;

    @OneToOne
    @JoinColumn(name = "linked_seat_id")
    private Seat linkedSeat = null;

    @OneToMany(mappedBy = "seat")
    private List<Ticket> tickets;

    public boolean hasPosition(int seatRow, int seatColumn) {
        return this.seatRow == seatRow && this.seatColumn == seatColumn;
    }
}
