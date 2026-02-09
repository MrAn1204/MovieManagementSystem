package com.mms.mms_api.business.service.validation;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.RoleRepository;
import com.mms.mms_api.data.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserValidationService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public boolean existsById(@NonNull UUID id) {
        return userRepository.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean existsByEmailAndIdNot(String email, UUID id) {
        return userRepository.existsByEmailAndIdNot(email, id);
    }

    public boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id) {
        return userRepository.existsByPhoneNumberAndIdNot(phoneNumber, id);
    }

    public boolean existsAllByIdIn(List<UUID> ids) {
        return roleRepository.existsAllByIdIn(ids);
    }
}
