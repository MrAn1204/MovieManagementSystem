package com.mms.mms_api.data;

import com.mms.mms_api.model.Movie;
import com.mms.mms_api.data.projection.UpcomingMovieProjection;

import java.time.LocalDate;
import java.util.List;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository for movie persistence and specification queries.
 */
public interface MovieRepository extends JpaRepository<Movie, UUID>, JpaSpecificationExecutor<Movie> {
    @Query("""
            SELECT m.id AS id, m.name AS name, m.releaseDate AS releaseDate
            FROM Movie m
            WHERE m.releaseDate >= :start AND m.releaseDate < :end
            ORDER BY m.releaseDate ASC, m.name ASC
            """)
    List<UpcomingMovieProjection> findUpcomingMovies(@Param("start") LocalDate start, @Param("end") LocalDate end);
}