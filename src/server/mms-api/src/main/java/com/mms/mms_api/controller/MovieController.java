package com.mms.mms_api.controller;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.query.movie.MovieGetAllQuery;
import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.business.query.movie.MovieSearchQuery;
import com.mms.mms_api.business.service.MovieService;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.movie.MovieDetailDto;
import com.mms.mms_api.dto.movie.MovieDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

/**
 * Provides CRUD and search endpoints for movies.
 */
@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    /**
     * Returns all movies.
     *
     * @return list of movies
     */
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {
        MovieGetAllQuery query = new MovieGetAllQuery();
        List<MovieDto> movies = movieService.handle(query);
        return ResponseEntity.ok(movies);
    }

    /**
     * Returns movie details by id.
     *
     * @param id movie identifier
     * @return movie details when found
     */
    @GetMapping("/{id}")
    public ResponseEntity<MovieDetailDto> getById(@PathVariable UUID id) {
        MovieGetByIdQuery query = new MovieGetByIdQuery(id);
        MovieDetailDto movie = movieService.handle(query);

        return movie != null
                ? ResponseEntity.ok(movie)
                : ResponseEntity.notFound().build();
    }

    /**
     * Creates a new movie.
     *
     * @param command create command payload
     * @return created movie
     */
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieDetailDto> create(@Valid @ModelAttribute MovieCreateCommand command) {
        MovieDetailDto result = movieService.handle(command);

        return result != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(result)
                : ResponseEntity.badRequest().build();
    }

    /**
     * Updates an existing movie by id.
     *
     * @param id movie identifier
     * @param command update command payload
     * @return updated movie when found
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieDetailDto> update(@PathVariable UUID id, @Valid @ModelAttribute MovieUpdateCommand command) {
        command.setId(id);

        MovieDetailDto updatedMovie = movieService.handle(command);

        return updatedMovie != null
                ? ResponseEntity.ok(updatedMovie)
                : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a movie by id.
     *
     * @param id movie identifier
     * @return no-content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        MovieDeleteCommand command = new MovieDeleteCommand(id);

        movieService.handle(command);

        return ResponseEntity.noContent().build();
    }

    /**
     * Searches movies with filter and pagination.
     *
     * @param query search criteria
     * @return paginated movie result
     */
    @PostMapping("/search")
    public ResponseEntity<PaginatedResult<MovieDto>> search(@Valid @RequestBody MovieSearchQuery query) {
        PaginatedResult<MovieDto> movies = movieService.handle(query);

        return ResponseEntity.ok(movies);
    }
}