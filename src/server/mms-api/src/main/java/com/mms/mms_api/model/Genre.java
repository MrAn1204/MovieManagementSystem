package com.mms.mms_api.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Genre extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "genres")
    private Collection<Movie> movies;
}
