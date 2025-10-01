package com.mms.mms_api.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends BaseEntity {
    private int totalMoney;

    private int addScore;

    private int useScore;

    private double discount;

    @OneToMany(mappedBy = "invoice")
    private List<Ticket> tickets;
}
