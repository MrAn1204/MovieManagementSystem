package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.business.service.UserService;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.dto.user.UserDto;
import com.mms.mms_api.util.validator.UserValidator;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    private UserValidator userValidator;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        UserGetAllQuery query = new UserGetAllQuery();

        List<UserDto> users = userService.handle(query);

        return ResponseEntity.ok(users);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserCreateCommand command) {
        userValidator.validate(command);

        UserDto result = userService.handle(command);

        return result != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(result)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getById(@PathVariable UUID id) {
        UserDetailDto user = userService.handle(new UserGetByIdQuery(id));

        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateCommand command) {
        command.setId(id);
        userValidator.validate(command);

        UserDto updatedUser = userService.handle(command);

        return updatedUser != null
                ? ResponseEntity.ok(updatedUser)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UserDeleteCommand command = new UserDeleteCommand(id);

        userService.handle(command);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<UserDto>> search(@Valid @RequestBody UserSearchQuery query) {
        PaginatedResult<UserDto> users = userService.handle(query);

        return ResponseEntity.ok(users);
    }
}
