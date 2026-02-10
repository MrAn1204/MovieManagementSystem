package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.GenreRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreValidationService {
    private final GenreRepository genreRepository;

    public boolean existsAllById(List<UUID> ids) {
        return genreRepository.existsAllByIdIn(ids);
    }

}
