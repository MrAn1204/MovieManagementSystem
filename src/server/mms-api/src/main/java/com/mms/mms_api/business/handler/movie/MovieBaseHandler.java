package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.util.mapper.MovieMapper;

/**
 * Base handler for movie-related requests.
 */
public abstract class MovieBaseHandler<I, O> extends BaseHandler<I, O> {
    protected MovieRepository movieRepository;

    protected MovieMapper movieMapper;

    /**
     * Creates a movie base handler.
     *
     * @param movieMapper movie mapper
     * @param movieRepository movie repository
     */
    protected MovieBaseHandler(MovieMapper movieMapper, MovieRepository movieRepository) {
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }

}
