package com.mms.mms_api.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import com.mms.mms_api.models.Gender;
import com.mms.mms_api.models.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private UUID id;

    private String username;

    private String fullname;

    private String password;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String email;

    private String citizenIdNumber;

    private String phoneNumber;

    private String address;

    private int score;

    private Collection<Role> roles;
}
