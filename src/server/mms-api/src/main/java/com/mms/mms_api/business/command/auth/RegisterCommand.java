package com.mms.mms_api.business.command.auth;

import java.time.LocalDate;

import com.mms.mms_api.annotation.PasswordMatch;
import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.common.AppConstant;
import com.mms.mms_api.model.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Command payload for user registration operations.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@PasswordMatch
public class RegisterCommand extends BaseCreateCommand {
    @NotNull(message = "{user.username.required}")
    @Size(min = AppConstant.USERNAME_MIN, max = AppConstant.USERNAME_MAX, message = "{user.username.size}")
    private String username;

    @NotNull(message = "{user.fullname.required}")
    @Size(min = AppConstant.FULLNAME_MIN, max = AppConstant.FULLNAME_MAX, message = "{user.fullname.size}")
    private String fullname;

    private String password;

    private String confirmPassword;

    @NotNull(message = "{user.gender.required}")
    private Gender gender;

    @NotNull(message = "{user.dob.required}")
    @Past(message = "{user.dob.past}")
    private LocalDate dateOfBirth;

    @Email(message = "{user.email.invalid}")
    private String email;

    @NotNull(message = "{user.phone.required}")
    @Size(min = AppConstant.PHONE_MIN, max = AppConstant.PHONE_MAX, message = "{user.phone.size}")
    private String phoneNumber;
}
