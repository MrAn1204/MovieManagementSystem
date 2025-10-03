package com.mms.mms_api.model;

import java.time.LocalDate;
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
public class Promotion extends BaseEntity {
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String image;

    private int discount;

    @OneToMany(mappedBy = "promotion")
    private List<Ticket> tickets;
}
