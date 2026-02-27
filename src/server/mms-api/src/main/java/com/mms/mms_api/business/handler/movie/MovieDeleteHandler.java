package com.mms.mms_api.business.handler.movie;

import java.util.UUID;

import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;

public class MovieDeleteHandler extends MovieBaseHandler<MovieDeleteCommand, Void> {
    private final GscService gscService;

    public MovieDeleteHandler(MovieDeleteCommand request, MovieRepository movieRepository, GscService gscService) {
        super(request, null, movieRepository);
        this.gscService = gscService;
    }

    @Override
    public Void execute() {
        UUID id = request.getId();
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("movie.notFound"));

        movieRepository.delete(movie);

        gscService.delete(movie.getThumbnail());

        return null;
    }
}
