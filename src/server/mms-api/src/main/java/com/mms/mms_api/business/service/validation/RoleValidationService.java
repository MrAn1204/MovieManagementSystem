package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleValidationService {
    private final RoleRepository roleRepository;

    public boolean existsAllByIdIn(List<UUID> ids) {
        return roleRepository.existsAllByIdIn(ids);
    }
}
