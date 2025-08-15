package com.mms.mms_api.model;


import java.util.List;

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
    private int seatQuantity;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "room")
    private List<Seat> seats;
}
