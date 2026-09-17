package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.user.UserCreateCommand;
import com.mms.mms_api.business.command.user.UserDeleteCommand;
import com.mms.mms_api.business.command.user.UserUpdateCommand;
import com.mms.mms_api.business.query.user.UserGetAllQuery;
import com.mms.mms_api.business.query.user.UserGetByIdQuery;
import com.mms.mms_api.business.query.user.UserSearchQuery;
import com.mms.mms_api.business.service.AuthenticationService;
import com.mms.mms_api.business.service.UserService;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.user.UserDetailDto;
import com.mms.mms_api.dto.user.UserDto;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Provides CRUD and search endpoints for users.
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    private AuthenticationService authenticationService;

    /**
     * Returns all users.
     *
     * @return list of users
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        UserGetAllQuery query = new UserGetAllQuery();

        List<UserDto> users = userService.handle(query);

        return ResponseEntity.ok(users);
    }

    /**
     * Creates a user account.
     *
     * @param command create payload
     * @return created user details
     */
    @PostMapping("/create")
    public ResponseEntity<UserDetailDto> create(@Valid @RequestBody UserCreateCommand command) {
        UserDetailDto result = userService.handle(command);

        return result != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(result)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Returns user details by id.
     *
     * @param id user identifier
     * @return user details when found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getById(@PathVariable UUID id) {
        UserDetailDto user = userService.handle(new UserGetByIdQuery(id));

        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    /**
     * Updates a user by id.
     *
     * @param id user identifier
     * @param command update payload
     * @return updated user when found
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailDto> update(@PathVariable UUID id, @Valid @RequestBody UserUpdateCommand command, HttpServletRequest http) {
        command.setId(id);

        UserDetailDto updatedUser = userService.handle(command);

        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }

        if (command.getPassword() != null && !command.getPassword().isEmpty()) {
            ResponseCookie cookie = authenticationService.getLogoutCookie();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(updatedUser);
        }

        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user by id.
     *
     * @param id user identifier
     * @return no-content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UserDeleteCommand command = new UserDeleteCommand(id);

        userService.handle(command);

        return ResponseEntity.noContent().build();
    }

    /**
     * Searches users with filter and pagination.
     *
     * @param query search criteria
     * @return paginated user result
     */
    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<UserDto>> search(@Valid @RequestBody UserSearchQuery query) {
        PaginatedResult<UserDto> users = userService.handle(query);

        return ResponseEntity.ok(users);
    }
}
