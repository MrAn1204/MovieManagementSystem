package com.mms.mms_api.business.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.TalentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TalentValidationService {
    private final TalentRepository talentRepository;

    public boolean existsAllById(List<UUID> ids) {
        return talentRepository.existsAllByIdIn(ids);
    }
}
