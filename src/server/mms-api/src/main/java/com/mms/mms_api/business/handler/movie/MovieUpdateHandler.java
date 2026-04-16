package com.mms.mms_api.business.handler.movie;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;
import com.mms.mms_api.util.mapper.MovieMapper;

import java.util.List;
import java.util.UUID;

/**
 * Handles movie update commands.
 */
@Component
public class MovieUpdateHandler extends MovieBaseHandler<MovieUpdateCommand, MovieDto> {
    private GenreRepository genreRepository;

    private LanguageRepository languageRepository;

    private StudioRepository studioRepository;

    private TalentRepository talentRepository;

    private GscService gscService;

    /**
     * Creates a MovieUpdateHandler.
     *
     * @param movieMapper movie mapper
     * @param movieRepository movie repository
     * @param genreRepository genre repository
     * @param languageRepository language repository
     * @param studioRepository studio repository
     * @param talentRepository talent repository
     * @param gscService Google Cloud Storage service for thumbnail management
     */
    public MovieUpdateHandler(
            MovieMapper movieMapper,
            MovieRepository movieRepository,
            GenreRepository genreRepository,
            LanguageRepository languageRepository,
            StudioRepository studioRepository,
            TalentRepository talentRepository,
            GscService gscService) {
        super(movieMapper, movieRepository);
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.studioRepository = studioRepository;
        this.talentRepository = talentRepository;
        this.gscService = gscService;
    }

    /**
     * Updates an existing movie, resolves associations and replaces the thumbnail if provided.
     *
     * @param request movie update command
     * @return updated movie DTO
     * @throws com.mms.mms_api.exception.ResourceNotFoundException when the movie does not exist
     */
    @Override
    public MovieDto execute(MovieUpdateCommand request) {
        Movie movie = movieRepository.findById(request.getId()).orElseThrow(
                () -> new ResourceNotFoundException("movie.notFound"));

        List<UUID> genreIds = request.getGenreIds();
        List<UUID> studioIds = request.getStudioIds();
        List<UUID> talentIds = request.getTalentIds();
        UUID languageId = request.getLanguageId();

        List<Genre> mappedGenres = null;
        if (genreIds != null) {
            mappedGenres = genreRepository.findAllById(genreIds);
        }

        List<Studio> mappedStudios = null;
        if (studioIds != null) {
            mappedStudios = studioRepository.findAllById(studioIds);
        }

        List<Talent> mappedTalents = null;
        if (talentIds != null) {
            mappedTalents = talentRepository.findAllById(talentIds);
        }

        Language mappedLanguage = null;
        if (languageId != null) {
            mappedLanguage = languageRepository.findById(languageId).orElse(null);
        }

        String oldThumbnailUrl = movie.getThumbnail();
        String newThumbnailUrl = gscService.upload(request.getThumbnail(), StoragePath.MOVIE_THUMBNAIL);

        movieMapper.updateEntity(request, movie);

        movie.setGenres(mappedGenres);
        movie.setLanguage(mappedLanguage);
        movie.setStudios(mappedStudios);
        movie.setTalents(mappedTalents);

        if (newThumbnailUrl != null) {
            movie.setThumbnail(newThumbnailUrl);
        }

        Movie updatedMovie = movieRepository.save(movie);

        if (newThumbnailUrl != null && oldThumbnailUrl != null) {
            gscService.delete(oldThumbnailUrl);
        }

        return movieMapper.toDto(updatedMovie);
    }
}