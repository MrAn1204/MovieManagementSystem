package com.mms.mms_api.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity representing an actor participating in a movie.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Talent extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String profile;

    @ManyToMany(mappedBy = "talents")
    private Collection<Movie> movies;
}
