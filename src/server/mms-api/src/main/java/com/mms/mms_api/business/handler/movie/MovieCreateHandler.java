package com.mms.mms_api.business.handler.movie;

import java.util.List;
import java.util.UUID;

import com.mms.mms_api.business.command.movie.MovieCreateCommand;
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

        Movie movie = movieMapper.toEntity(request);

        movie.setGenres(mappedGenres);
        movie.setLanguage(mappedLanguage);
        movie.setStudios(mappedStudios);
        movie.setTalents(mappedTalents);

        Movie savedMovie = movieRepository.save(movie);

        return movieMapper.toDto(savedMovie);
    }
}
