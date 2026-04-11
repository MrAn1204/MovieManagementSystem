package com.mms.mms_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.query.genre.GenreGetAllQuery;
import com.mms.mms_api.business.service.GenreService;
import com.mms.mms_api.dto.movie.GenreDto;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Exposes read endpoints for movie genres.
 */
@RestController
@RequestMapping("/api/genres")
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;

    /**
     * Returns all available genres.
     *
     * @return list of genres
     */
    @GetMapping
    public ResponseEntity<List<GenreDto>> getAll() {
        GenreGetAllQuery request = new GenreGetAllQuery();

        List<GenreDto> genres = genreService.handle(request);

        return ResponseEntity.ok(genres);
    }
}
