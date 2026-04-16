package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.MovieRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for movie existence checks.
 */
@Service
@AllArgsConstructor
public class MovieValidationService {
    private final MovieRepository movieRepository;

    /**
     * Checks whether a movie exists by id.
     *
     * @param id movie identifier
     * @return true when the movie exists
     */
    public boolean existsById(@NonNull UUID id) {
        return movieRepository.existsById(id);
    }
}
