package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.StudioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudioValidationService {
    private final StudioRepository studioRepository;

    public boolean existsAllById(List<UUID> ids) {
        return studioRepository.existsAllByIdIn(ids);
    }
}
