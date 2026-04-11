package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.GenreRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for genre existence checks.
 */
@Service
@AllArgsConstructor
public class GenreValidationService {
    private final GenreRepository genreRepository;

    /**
     * Checks whether all given genre ids exist.
     *
     * @param ids genre identifiers
     * @return true when all identifiers exist
     */
    public boolean existsAllById(List<UUID> ids) {
        return genreRepository.existsAllByIdIn(ids);
    }

}
