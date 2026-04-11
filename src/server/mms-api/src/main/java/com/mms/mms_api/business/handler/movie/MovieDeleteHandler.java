package com.mms.mms_api.business.handler.movie;

import org.springframework.stereotype.Component;

import java.util.UUID;

import com.mms.mms_api.business.command.movie.MovieDeleteCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Movie;

/**
 * Handles movie delete commands.
 */
@Component
public class MovieDeleteHandler extends MovieBaseHandler<MovieDeleteCommand, Void> {
    private final GscService gscService;

    /**
     * Creates a MovieDeleteHandler.
     *
     * @param movieRepository movie repository
     * @param gscService Google Cloud Storage service for thumbnail deletion
     */
    public MovieDeleteHandler(MovieRepository movieRepository, GscService gscService) {
        super(null, movieRepository);
        this.gscService = gscService;
    }

    /**
     * Deletes a movie and removes its associated thumbnail from storage.
     *
     * @param request delete command containing the target movie id
     * @return {@code null}
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the movie does not exist
     */
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
