package com.mms.mms_api.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity representing an invoice for one or more tickets.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends AuditableEntity {
    @Column(nullable = false)
    private int totalMoney;

    private int addScore = 0;

    private int useScore = 0;

    @Column(nullable = false)
    private double discount;

    @OneToMany(mappedBy = "invoice")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setTotalMoney(List<Ticket> tickets, double discount, int useScore) {
        double result = tickets.stream().mapToDouble(ticket -> {
            double price = ticket.getPrice();

            if (ticket.getPromotion() != null) {
                price = price * (1 - ticket.getPromotion().getDiscount());
            }

            return (int) price;
        }).sum();

        result = result * (1 - discount) - useScore;

        this.totalMoney = (int) Math.round(result);
    }
}
