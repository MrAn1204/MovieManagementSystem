package com.mms.mms_api.dto.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.BaseDto;
import com.mms.mms_api.dto.invoice.InvoiceDto;
import com.mms.mms_api.model.Gender;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing detailed user data.
 *
 * Maps from {@link com.mms.mms_api.model.User User}.
 * Includes roles and invoices.
 *
 * @see com.mms.mms_api.model.User User
 * @see RoleDto RoleDto
 * @see com.mms.mms_api.dto.invoice.InvoiceDto InvoiceDto
 */
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AuditDto audit;
}
