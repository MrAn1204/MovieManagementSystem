package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.query.studio.StudioGetAllQuery;
import com.mms.mms_api.business.service.StudioService;
import com.mms.mms_api.dto.movie.StudioDto;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Exposes read endpoints for movie studios.
 */
@RestController
@RequestMapping("/api/studios")
@AllArgsConstructor
public class StudioController {
    private final StudioService studioService;

    /**
     * Returns all available studios.
     *
     * @return list of studios
     */
    @GetMapping
    public ResponseEntity<List<StudioDto>> getAll() {
        StudioGetAllQuery request = new StudioGetAllQuery();

        List<StudioDto> studios = studioService.handle(request);

        return ResponseEntity.ok(studios);
    }
}
