package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.handler.BaseHandler;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.util.mapper.MovieMapper;

public abstract class MovieBaseHandler<I, O> extends BaseHandler<I, O> {
    protected MovieRepository movieRepository;

    protected MovieMapper movieMapper;

    protected MovieBaseHandler(I request, MovieMapper movieMapper, MovieRepository movieRepository) {
        super(request);
        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
    }

}
