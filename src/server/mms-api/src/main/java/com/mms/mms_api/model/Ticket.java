package com.mms.mms_api.model;

import com.mms.mms_api.common.AppConstant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Ticket extends BaseEntity {
    public Ticket(Ticket ticket) {
        this.price = ticket.price;
        this.schedule = ticket.schedule;
        this.seat = ticket.seat;
        this.invoice = ticket.invoice;
        this.promotion = ticket.promotion;
        this.user = ticket.user;
    }

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPrice(SeatType seatType) {
        int base = AppConstant.BASE_SEAT_PRICE;

        this.price = (int) (base * seatType.getMultiplier());
    }
}
