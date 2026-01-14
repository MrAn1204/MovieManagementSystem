package com.mms.mms_api.dto.user;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.mms.mms_api.dto.InvoiceDto;
import com.mms.mms_api.dto.RoleDto;
import com.mms.mms_api.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private final UUID id;

    private final String username;

    private final String fullname;

    private final Gender gender;

    private final LocalDate dateOfBirth;

    private final String email;

    private final String citizenIdNumber;

    private final String phoneNumber;

    private final String address;

    private final int score;

    private final List<RoleDto> roles;

    private final List<InvoiceDto> invoices;
}
