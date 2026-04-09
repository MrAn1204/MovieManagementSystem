package com.mms.mms_api.business.handler.movie;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;

@Component
public class MovieDeleteHandler extends MovieBaseHandler<MovieDeleteCommand, Void> {
    private final GscService gscService;

    public MovieDeleteHandler(MovieRepository movieRepository, GscService gscService) {
        super(null, movieRepository);
        this.gscService = gscService;
    }

    @Override
    public Void execute(MovieDeleteCommand request) {
        UUID id = request.getId();
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("movie.notFound"));

        movieRepository.delete(movie);

        gscService.delete(movie.getThumbnail());

        return null;
    }
}
