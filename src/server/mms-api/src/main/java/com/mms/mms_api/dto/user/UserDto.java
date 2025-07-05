package com.mms.mms_api.dto.user;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import com.mms.mms_api.models.Gender;
import com.mms.mms_api.models.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private final UUID id;

    private final String username;

    private final String fullname;

    private final String password;

    private final Gender gender;

    private final LocalDate dateOfBirth;

    private final String email;

    private final String citizenIdNumber;

    private final String phoneNumber;

    private final String address;

    private final int score;

    private final Collection<Role> roles;
}
