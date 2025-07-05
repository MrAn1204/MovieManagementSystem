package com.mms.mms_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.commands.user.UserCreateCommand;
import com.mms.mms_api.business.commands.user.UserDeleteCommand;
import com.mms.mms_api.business.commands.user.UserUpdateCommand;
import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.business.services.UserService;
import com.mms.mms_api.dto.user.UserDto;

import java.util.Collection;
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


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Collection<UserDto>> getAll() {
        UserGetAllQuery query = new UserGetAllQuery();
        
        Collection<UserDto> users = userService.handle(query);

        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody UserCreateCommand command) {
        UserDto result = userService.handle(command);
        
        return result != null 
            ? ResponseEntity.status(HttpStatus.CREATED).body(result) 
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable UUID id) {
        UserDto user = userService.handle(new UserGetByIdQuery(id));
        
        return user != null
            ? ResponseEntity.ok(user)
            : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody UserUpdateCommand command) {
        command.setId(id);

        UserDto updatedUser = userService.handle(command);

        return updatedUser != null
            ? ResponseEntity.ok(updatedUser)
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UserDeleteCommand command = new UserDeleteCommand(id);

        boolean deleted = userService.handle(command);

        return deleted
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}
