package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.query.talent.TalentGetAllQuery;
import com.mms.mms_api.business.service.TalentService;
import com.mms.mms_api.dto.movie.TalentDto;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Exposes read endpoints for movie talents.
 */
@RestController
@RequestMapping("/api/talents")
@AllArgsConstructor
public class TalentController {
    private final TalentService talentService;

    /**
     * Returns all available talents.
     *
     * @return list of talents
     */
    @GetMapping
    public ResponseEntity<List<TalentDto>> getAll() {
        TalentGetAllQuery request = new TalentGetAllQuery();

        List<TalentDto> talents = talentService.handle(request);

        return ResponseEntity.ok(talents);
    }
}
