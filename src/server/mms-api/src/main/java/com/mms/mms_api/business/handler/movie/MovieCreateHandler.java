package com.mms.mms_api.business.handler.movie;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.business.service.GscService;
import com.mms.mms_api.common.StoragePath;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.movie.MovieDto;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;
import com.mms.mms_api.util.mapper.MovieMapper;

/**
 * Handles movie creation commands.
 */
@Component
public class MovieCreateHandler extends MovieBaseHandler<MovieCreateCommand, MovieDto> {
    private final GenreRepository genreRepository;

    private final LanguageRepository languageRepository;

    private final StudioRepository studioRepository;

    private final TalentRepository talentRepository;

    private final GscService gscService;

    /**
     * Creates a MovieCreateHandler.
     *
     * @param movieMapper movie mapper
     * @param movieRepository movie repository
     * @param genreRepository genre repository
     * @param languageRepository language repository
     * @param studioRepository studio repository
     * @param talentRepository talent repository
     * @param gscService Google Cloud Storage service for thumbnail upload
     */
    public MovieCreateHandler(
            MovieMapper movieMapper,
            MovieRepository movieRepository,
            GenreRepository genreRepository,
            LanguageRepository languageRepository,
            StudioRepository studioRepository,
            TalentRepository talentRepository,
            GscService gscService) {
        super(movieMapper, movieRepository);
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.studioRepository = studioRepository;
        this.talentRepository = talentRepository;
        this.gscService = gscService;
    }

    /**
     * Creates a new movie, resolves associations, uploads the thumbnail and persists the entity.
     *
     * @param request movie create command
     * @return created movie DTO
     */
    public MovieDto execute(MovieCreateCommand request) {
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
        
        String thumbnailUrl = null;
        if (request.getThumbnail() != null) {
            thumbnailUrl = gscService.upload(request.getThumbnail(), StoragePath.MOVIE_THUMBNAIL);
        }

        Movie movie = movieMapper.toEntity(request);

        movie.setGenres(mappedGenres);
        movie.setLanguage(mappedLanguage);
        movie.setStudios(mappedStudios);
        movie.setTalents(mappedTalents);
        movie.setThumbnail(thumbnailUrl);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toDto(savedMovie);
    }
}
