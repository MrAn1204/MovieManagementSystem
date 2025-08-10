package com.mms.mms_api.business.handler.movie;

import java.util.List;

import org.springframework.util.StringUtils;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.exception.ErrorMessage;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;
import com.mms.mms_api.util.mapper.MovieMapper;

public class MovieCreateHandler extends MovieBaseHandler<MovieCreateCommand, MovieDto> {
    private GenreRepository genreRepository;

    private LanguageRepository languageRepository;

    private StudioRepository studioRepository;

    private TalentRepository talentRepository;

    public MovieCreateHandler(
            MovieCreateCommand request,
            MovieMapper movieMapper,
            MovieRepository movieRepository,
            GenreRepository genreRepository,
            LanguageRepository languageRepository,
            StudioRepository studioRepository,
            TalentRepository talentRepository) {
        super(request, movieMapper, movieRepository);
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.studioRepository = studioRepository;
        this.talentRepository = talentRepository;
    }

    public MovieDto execute() {
        validateRequest();

        Movie movie = movieMapper.toEntity(request);

        List<Genre> genres = genreRepository.findByNameIn(request.getGenres());

        Language language = languageRepository.findByName(request.getLanguage());

        List<Studio> studios = studioRepository.findByNameIn(request.getStudios());

        List<Talent> talents = talentRepository.findByNameIn(request.getTalents());

        movie.setGenres(genres);
        movie.setLanguage(language);
        movie.setStudios(studios);
        movie.setTalents(talents);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toDto(savedMovie);
    }

    private void validateRequest() {
        if (StringUtils.hasText(request.getName())) {
            throw new InvalidInputException(ErrorMessage.NAME_REQUIRED);
        }
    }
}
