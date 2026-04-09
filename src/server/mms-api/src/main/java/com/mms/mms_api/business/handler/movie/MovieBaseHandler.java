package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.util.mapper.MovieMapper;

public abstract class MovieBaseHandler<I, O> extends BaseHandler<I, O> {
    protected MovieRepository movieRepository;

    protected MovieMapper movieMapper;

    protected MovieBaseHandler(MovieMapper movieMapper, MovieRepository movieRepository) {
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }

}
