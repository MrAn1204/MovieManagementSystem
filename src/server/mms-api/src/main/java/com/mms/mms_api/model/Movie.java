package com.mms.mms_api.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entity representing a movie and its production metadata.
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Movie extends AuditableEntity {
    @Column(nullable = false)
    private String name;
    
    private LocalDate releaseDate;
    
    private int duration;
    
    private String content;
    
    private String thumbnail;
    
    private double rating;

    @ManyToMany
    @JoinTable(
        name = "movie_genres",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
        name = "movie_studios",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "studio_id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<Studio> studios;

    @ManyToMany
    @JoinTable(
        name = "movie_talents",
        joinColumns = @JoinColumn(name = "movie_id"),
        inverseJoinColumns = @JoinColumn(name = "talent_id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<Talent> talents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id")
    private Language language;

    @OneToMany(mappedBy = "movie")
    private List<Schedule> schedules;
}
