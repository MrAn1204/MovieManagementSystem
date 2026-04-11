package com.mms.mms_api.business.service.validation;

import java.util.UUID;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.mms.mms_api.data.UserRepository;

import lombok.AllArgsConstructor;

/**
 * Validation helper for user existence and uniqueness checks.
 */
@Service
@AllArgsConstructor
public class UserValidationService {
    private UserRepository userRepository;

    /**
     * Checks whether a user exists by id.
     *
     * @param id user identifier
     * @return true when the user exists
     */
    public boolean existsById(@NonNull UUID id) {
        return userRepository.existsById(id);
    }

    /**
     * Checks whether a username already exists.
     *
     * @param username username
     * @return true when the username exists
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Checks whether an email already exists.
     *
     * @param email email address
     * @return true when the email exists
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Checks whether a phone number already exists.
     *
     * @param phoneNumber phone number
     * @return true when the phone number exists
     */
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    /**
     * Checks whether an email exists on another user.
     *
     * @param email email address
     * @param id excluded user identifier
     * @return true when used by another user
     */
    public boolean existsByEmailAndIdNot(String email, UUID id) {
        return userRepository.existsByEmailAndIdNot(email, id);
    }

    /**
     * Checks whether a phone number exists on another user.
     *
     * @param phoneNumber phone number
     * @param id excluded user identifier
     * @return true when used by another user
     */
    public boolean existsByPhoneNumberAndIdNot(String phoneNumber, UUID id) {
        return userRepository.existsByPhoneNumberAndIdNot(phoneNumber, id);
    }
}
