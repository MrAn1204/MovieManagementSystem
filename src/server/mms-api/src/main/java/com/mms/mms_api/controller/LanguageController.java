package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.query.language.LanguageGetAllQuery;
import com.mms.mms_api.business.service.LanguageService;
import com.mms.mms_api.dto.LanguageDto;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/languages")
@AllArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<LanguageDto>> getAll() {
        LanguageGetAllQuery request = new LanguageGetAllQuery();

        List<LanguageDto> languages = languageService.handle(request);

        return ResponseEntity.ok(languages);
    }
}
