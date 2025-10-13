package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.query.studio.StudioGetAllQuery;
import com.mms.mms_api.business.service.StudioService;
import com.mms.mms_api.dto.StudioDto;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/studios")
@AllArgsConstructor
public class StudioController {
    private final StudioService studioService;

    @GetMapping
    public ResponseEntity<List<StudioDto>> getAll() {
        StudioGetAllQuery request = new StudioGetAllQuery();

        List<StudioDto> studios = studioService.handle(request);

        return ResponseEntity.ok(studios);
    }
}
