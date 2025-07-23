package com.mms.mms_api.business.handler.movie;

import com.mms.mms_api.business.command.movie.MovieUpdateCommand;
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

import java.util.List;
import java.util.Optional;

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
            TalentRepository talentRepository
    ) {
        super(request, movieMapper, movieRepository);
        this.genreRepository = genreRepository;
        this.languageRepository = languageRepository;
        this.studioRepository = studioRepository;
        this.talentRepository = talentRepository;
    }

    @Override
    public MovieDto execute() {
        Optional<Movie> optionalMovie = movieRepository.findById(request.getId());

        if (optionalMovie.isEmpty()) {
            return null;
        }

        List<Genre> genres = genreRepository.findByNameIn(request.getGenres());

        Language language = languageRepository.findByName(request.getLanguage());

        List<Studio> studios = studioRepository.findByNameIn(request.getStudios());

        List<Talent> talents = talentRepository.findByNameIn(request.getTalents());

        Movie movie = optionalMovie.get();
        
        movieMapper.updateEntity(request, movie);
        movie.setGenres(genres);
        movie.setLanguage(language);
        movie.setStudios(studios);
        movie.setTalents(talents);

        Movie updatedMovie = movieRepository.save(movie);

        return movieMapper.toDto(updatedMovie);
    }
}