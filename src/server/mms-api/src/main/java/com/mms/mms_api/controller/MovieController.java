package com.mms.mms_api.controller;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.query.movie.MovieGetAllQuery;
import com.mms.mms_api.business.query.movie.MovieGetByIdQuery;
import com.mms.mms_api.business.query.movie.MovieSearchQuery;
import com.mms.mms_api.business.service.MovieService;
import com.mms.mms_api.common.PaginatedResult;
import com.mms.mms_api.dto.MovieDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getAll() {
        MovieGetAllQuery query = new MovieGetAllQuery();
        List<MovieDto> movies = movieService.handle(query);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getById(@PathVariable UUID id) {
        MovieGetByIdQuery query = new MovieGetByIdQuery(id);
        MovieDto movie = movieService.handle(query);

        return movie != null
                ? ResponseEntity.ok(movie)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<MovieDto> create(@RequestBody MovieCreateCommand command) {
        MovieDto result = movieService.handle(command);

        return result != null
                ? ResponseEntity.status(HttpStatus.CREATED).body(result)
                : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> update(@PathVariable UUID id, @RequestBody MovieUpdateCommand command) {
        command.setId(id);

        MovieDto updatedMovie = movieService.handle(command);

        return updatedMovie != null
                ? ResponseEntity.ok(updatedMovie)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        MovieDeleteCommand command = new MovieDeleteCommand(id);

        movieService.handle(command);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResult<MovieDto>> search(@RequestBody MovieSearchQuery query) {
        PaginatedResult<MovieDto> movies = movieService.handle(query);

        return ResponseEntity.ok(movies);
    }
}