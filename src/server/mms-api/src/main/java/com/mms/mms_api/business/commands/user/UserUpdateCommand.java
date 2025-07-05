package com.mms.mms_api.business.commands.user;

import java.time.LocalDate;
import java.util.Collection;

import com.mms.mms_api.business.commands.BaseUpdateCommand;
import com.mms.mms_api.models.Gender;
import com.mms.mms_api.models.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserUpdateCommand extends BaseUpdateCommand {
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