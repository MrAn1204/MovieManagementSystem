package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.exception.ResourceNotFoundException;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;
import com.mms.mms_api.util.mapper.MovieMapper;
import com.mms.mms_api.util.validator.MovieValidator;

import java.util.List;
import java.util.UUID;

public class MovieUpdateHandler extends MovieBaseHandler<MovieUpdateCommand, MovieDto> {
    private GenreRepository genreRepository;

    private LanguageRepository languageRepository;

    private StudioRepository studioRepository;

    private TalentRepository talentRepository;

    public MovieUpdateHandler(
            MovieUpdateCommand request,
            MovieMapper movieMapper,
            MovieRepository movieRepository,
            GenreRepository genreRepository,
            LanguageRepository languageRepository,
            StudioRepository studioRepository,
            TalentRepository talentRepository) {
        super(request, movieMapper, movieRepository);
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.studioRepository = studioRepository;
        this.talentRepository = talentRepository;
    }

    @Override
    public MovieDto execute() {
        UUID movieId = request.getId();

        Movie movie;
        if (movieId == null || (movie = movieRepository.findById(movieId).orElse(null)) == null) {
            throw new ResourceNotFoundException("movie.notFound");
        }

        List<UUID> genreIds = request.getGenreIds();
        List<UUID> studioIds = request.getStudioIds();
        List<UUID> talentIds = request.getTalentIds();
        UUID languageId = request.getLanguageId();

        List<Genre> mappedGenres = null;
        if (genreIds != null) {
            mappedGenres = genreRepository.findAllById(genreIds);
            MovieValidator.validateGenres(genreIds, mappedGenres);
        }

        List<Studio> mappedStudios = null;
        if (studioIds != null) {
            mappedStudios = studioRepository.findAllById(studioIds);
            MovieValidator.validateStudios(studioIds, mappedStudios);
        }

        List<Talent> mappedTalents = null;
        if (talentIds != null) {
            mappedTalents = talentRepository.findAllById(talentIds);
            MovieValidator.validateTalents(talentIds, mappedTalents);
        }

        Language mappedLanguage = null;
        if (languageId != null) {
            mappedLanguage = languageRepository.findById(languageId).orElse(null);
            MovieValidator.validateLanguage(languageId, mappedLanguage);
        }

        movieMapper.updateEntity(request, movie);

        movie.setGenres(mappedGenres);
        movie.setLanguage(mappedLanguage);
        movie.setStudios(mappedStudios);
        movie.setTalents(mappedTalents);

        Movie updatedMovie = movieRepository.save(movie);

        return movieMapper.toDto(updatedMovie);
    }
}