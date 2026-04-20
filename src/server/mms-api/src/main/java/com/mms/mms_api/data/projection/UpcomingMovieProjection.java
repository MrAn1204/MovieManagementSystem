package com.mms.mms_api.data.projection;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Projection for upcoming movie rows in dashboard statistics.
 */
public interface UpcomingMovieProjection {
    UUID getId();

    String getName();

    LocalDate getReleaseDate();
}