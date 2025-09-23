package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;

public class MovieDeleteHandler extends MovieBaseHandler<MovieDeleteCommand, Void> {
    public MovieDeleteHandler(MovieDeleteCommand request, MovieRepository movieRepository) {
        super(request, null, movieRepository);
    }

    @Override
    public Void execute() {
        Movie movie = movieRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("movie.notFound"));

        movieRepository.delete(movie);

        return null;
    }
}
