package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.data.MovieRepository;

public class MovieDeleteHandler extends MovieBaseHandler<MovieDeleteCommand, Boolean> {
    public MovieDeleteHandler(MovieDeleteCommand request, MovieRepository movieRepository) {
        super(request, null, movieRepository);
    }

    @Override
    public Boolean execute() {
        if (!movieRepository.existsById(request.getId())) {
            return false;
        }

        movieRepository.deleteById(request.getId());
        return true;
    }
}
