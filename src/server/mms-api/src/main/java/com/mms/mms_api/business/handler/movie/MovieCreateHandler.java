package com.mms.mms_api.business.handler.movie;

import java.util.List;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.data.LanguageRepository;
import com.mms.mms_api.data.MovieRepository;
import com.mms.mms_api.data.StudioRepository;
import com.mms.mms_api.data.TalentRepository;
import com.mms.mms_api.dto.MovieDto;
import com.mms.mms_api.model.Genre;
import com.mms.mms_api.model.Language;
import com.mms.mms_api.model.Movie;
import com.mms.mms_api.model.Studio;
import com.mms.mms_api.model.Talent;
import com.mms.mms_api.util.mapper.MovieMapper;
import com.mms.mms_api.util.validator.MovieValidator;

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
        List<String> genreNames = request.getGenres();
        List<Genre> mappedGenres = genreRepository.findByNameIn(genreNames);
        MovieValidator.validateGenres(genreNames, mappedGenres);

        String languageName = request.getLanguage();
        Language mappedLanguage = languageRepository.findByName(languageName);
        MovieValidator.validateLanguage(languageName, mappedLanguage);

        List<String> studioNames = request.getStudios();
        List<Studio> mappedStudios = studioRepository.findByNameIn(studioNames);
        MovieValidator.validateStudios(studioNames, mappedStudios);

        List<String> talentNames = request.getTalents();
        List<Talent> mappedTalents = talentRepository.findByNameIn(talentNames);
        MovieValidator.validateTalents(talentNames, mappedTalents);

        Movie movie = movieMapper.toEntity(request);

        movie.setGenres(mappedGenres);
        movie.setLanguage(mappedLanguage);
        movie.setStudios(mappedStudios);
        movie.setTalents(mappedTalents);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toDto(savedMovie);
    }
}
