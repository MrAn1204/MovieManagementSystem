package com.mms.mms_api.dto.user;

import java.time.LocalDate;
import java.util.List;

import com.mms.mms_api.dto.BaseDto;
import com.mms.mms_api.dto.invoice.InvoiceDto;
import com.mms.mms_api.model.Gender;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDetailDto extends BaseDto {
    private String username;

    private String fullname;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String email;

    private String citizenIdNumber;

    private String phoneNumber;

    private String address;

    private int score;

    private List<RoleDto> roles;

    private List<InvoiceDto> invoices;
}
