package com.mms.mms_api.business.handler.movie;

import java.util.UUID;

import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;

public class MovieDeleteHandler extends MovieBaseHandler<MovieDeleteCommand, Void> {
    public MovieDeleteHandler(MovieDeleteCommand request, MovieRepository movieRepository) {
        super(request, null, movieRepository);
    }

    @Override
    public Void execute() {
        UUID id = request.getId();
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("movie.notFound");
        }

        movieRepository.deleteById(id);
        return null;
    }
}
