package com.mms.mms_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.commands.user.UserCreateCommand;
import com.mms.mms_api.business.commands.user.UserGetAllQuery;
import com.mms.mms_api.business.commands.user.UserGetByIdQuery;
import com.mms.mms_api.business.services.UserService;
import com.mms.mms_api.dto.UserDto;

import java.util.Collection;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Collection<UserDto> getAll() {
        UserGetAllQuery query = new UserGetAllQuery();
        
        return userService.handle(query);
    }
    
    @PostMapping("/create")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        UserCreateCommand command = new UserCreateCommand(userDto);
        
        UserDto result = userService.handle(command);
        
        return result != null 
            ? ResponseEntity.status(HttpStatus.CREATED).body(result) 
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable String id) {
        UserGetByIdQuery query = new UserGetByIdQuery(UUID.fromString(id));
        
        return userService.handle(query);
    }
}
