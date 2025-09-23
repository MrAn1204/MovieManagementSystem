package com.mms.mms_api.business.command.user;

import java.time.LocalDate;
import java.util.List;

import com.mms.mms_api.annotation.Password;
import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.model.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserCreateCommand extends BaseCreateCommand {
    @NotNull(message = "{user.username.required}")
    @Size(min = AppConstant.USERNAME_MIN, max = AppConstant.USERNAME_MAX, message = "{user.username.size}")
    private String username;

    @NotNull(message = "{user.fullname.required}")
    @Size(min = AppConstant.FULLNAME_MIN, max = AppConstant.FULLNAME_MAX, message = "{user.fullname.size}")
    private String fullname;

    @Password
    private String password;

    @NotNull(message = "{user.gender.required}")
    private Gender gender;

    @NotNull(message = "{user.dob.required}")
    @Past(message = "{user.dob.past}")
    private LocalDate dateOfBirth;

    @Email(message = "{user.email.invalid}")
    private String email;

    @Size(min = AppConstant.CITIZEN_ID_MIN, message = "{user.citizenId.size}")
    private String citizenIdNumber;

    @NotNull(message = "{user.phone.required}")
    @Size(min = AppConstant.PHONE_MIN, max = AppConstant.PHONE_MAX, message = "{user.phone.size}")
    private String phoneNumber;

    @Size(min = AppConstant.ADDRESS_MIN, max = AppConstant.ADDRESS_MAX, message = "{user.address.size}")
    private String address;

    @NotEmpty(message = "{user.roles.required}")
    private List<String> roles;
}
