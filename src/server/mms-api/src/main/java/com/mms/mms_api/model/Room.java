package com.mms.mms_api.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Room extends BaseEntity {
    @Column(nullable = false)
    private int rowLength;

    @Column(nullable = false)
    private int columnLength;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Seat> seats;

    public Seat getSeatAt(int seatRow, int seatColumn) {
        if (!isValidRow(seatRow) || !isValidColumn(seatColumn)) {
            return null;
        }

        return seats.stream()
                .filter(seat -> seat.hasPosition(seatRow, seatColumn))
                .findFirst()
                .orElse(null);
    }

    public boolean hasSpace() {
        return seats.size() < rowLength * columnLength;
    }

    public boolean hasSeatAt(int seatRow, int seatColumn) {
        if (!isValidRow(seatRow) || !isValidColumn(seatColumn)) {
            return false;
        }

        return seats.stream().anyMatch(seat -> seat.hasPosition(seatRow, seatColumn));
    }

    public boolean hasOtherSeatAt(int seatRow, int seatColumn, UUID excludeId) {
        if (!isValidRow(seatRow) || !isValidColumn(seatColumn)) {
            return false;
        }

        Seat seat = getSeatAt(seatRow, seatColumn);

        return seat != null && !seat.getId().equals(excludeId);
    }

    public boolean hasSeatFrom(int seatRow, int seatColumn) {
        if (!isValidRow(seatRow) || !isValidColumn(seatColumn)) {
            return false;
        }

        return seats.stream().anyMatch(seat -> seat.getSeatRow() >= seatRow || seat.getSeatColumn() >= seatColumn);
    }

    public boolean isValidRow(int row) {
        return row >= 1 && row <= columnLength;
    }

    public boolean isValidColumn(int column) {
        return column >= 1 && column <= rowLength;
    }

}
