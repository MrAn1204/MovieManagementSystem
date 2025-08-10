package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.ResourceNotFoundException;

public class MovieDeleteHandler extends MovieBaseHandler<MovieDeleteCommand, Void> {
    public MovieDeleteHandler(MovieDeleteCommand request, MovieRepository movieRepository) {
        super(request, null, movieRepository);
    }

    @Override
    public Void execute() {
        if (!movieRepository.existsById(request.getId())) {
            throw new ResourceNotFoundException(ErrorMessage.MOVIE_NOT_FOUND);
        }

        movieRepository.deleteById(request.getId());
        return null;
    }
}
