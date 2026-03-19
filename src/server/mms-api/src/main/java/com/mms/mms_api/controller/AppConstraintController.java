package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.service.AppConstraintService;

import lombok.AllArgsConstructor;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/app-constraints")
@AllArgsConstructor
public class AppConstraintController {
    private final AppConstraintService appConstraintService;

    @GetMapping()
    public Map<String, String> getAllConstraints() {
        return appConstraintService.getAllConstraints();
    }
    
}
