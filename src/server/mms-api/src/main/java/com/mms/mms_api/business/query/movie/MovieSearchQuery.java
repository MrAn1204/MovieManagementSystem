package com.mms.mms_api.business.query.movie;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.mms.mms_api.business.query.BaseSearchQuery;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Query payload for searching movies.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MovieSearchQuery extends BaseSearchQuery {
    private UUID languageId;
    
    private List<@NotNull(message = "{movie.genres.invalid}") UUID> genreIds;

    private List<@NotNull(message = "{movie.studios.invalid}") UUID> studioIds;

    private LocalDate releaseAfter;

    private LocalDate releaseBefore;
}
