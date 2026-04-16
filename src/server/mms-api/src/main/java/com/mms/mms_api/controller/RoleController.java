package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.query.role.RoleGetAllQuery;
import com.mms.mms_api.business.service.RoleService;
import com.mms.mms_api.dto.user.RoleDto;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Exposes read endpoints for user roles.
 */
@RestController
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    /**
     * Returns all available roles.
     *
     * @return list of roles
     */
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAll() {
        RoleGetAllQuery request = new RoleGetAllQuery();

        List<RoleDto> roles = roleService.handle(request);

        return ResponseEntity.ok(roles);
    }

}
