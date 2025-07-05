package com.mms.mms_api.business.commands.user;

import java.time.LocalDate;
import java.util.Collection;

import com.mms.mms_api.business.commands.BaseCreateCommand;
import com.mms.mms_api.models.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateCommand implements BaseCreateCommand {
    private String username;

    private String fullname;

    private String password;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    private String address;

    private Collection<String> roles;
}
