package com.mms.mms_api.business.command.user;

import java.time.LocalDate;
import java.util.List;

import com.mms.mms_api.business.command.BaseCreateCommand;
import com.mms.mms_api.model.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class UserCreateCommand extends BaseCreateCommand {
    private String username;

    private String fullname;

    private String password;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    private String address;

    private List<String> roles;
}
