package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mms.mms_api.data.RoleRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for role existence checks.
 */
@Service
@AllArgsConstructor
public class RoleValidationService {
    private final RoleRepository roleRepository;

    /**
     * Checks whether all given role ids exist.
     *
     * @param ids role identifiers
     * @return true when all identifiers exist
     */
    public boolean existsAllByIdIn(List<UUID> ids) {
        return roleRepository.existsAllByIdIn(ids);
    }
}
