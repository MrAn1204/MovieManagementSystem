package com.mms.mms_api.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity representing a spoken language for movies.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Language extends BaseEntity {
    @Column(nullable = false)
    private String name;

    private String nativeName;

    @OneToMany(mappedBy = "language")
    private Collection<Movie> movies;
}
