package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.MovieRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MovieValidationService {
    private final MovieRepository movieRepository;

    public boolean existsById(@NonNull UUID id) {
        return movieRepository.existsById(id);
    }
}
