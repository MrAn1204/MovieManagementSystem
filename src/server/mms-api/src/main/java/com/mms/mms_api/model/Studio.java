package com.mms.mms_api.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity representing a movie production studio.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Studio extends AuditableEntity {
    @Column(nullable = false)
    private String name;

    private String profile;

    @ManyToMany(mappedBy = "studios")
    private Collection<Movie> movies;
}
