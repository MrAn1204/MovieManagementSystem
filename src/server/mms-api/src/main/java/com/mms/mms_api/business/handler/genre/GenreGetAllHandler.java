package com.mms.mms_api.business.handler.genre;
import com.mms.mms_api.util.mapper.GenreMapper;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.dto.movie.GenreDto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.genre.GenreGetAllQuery;

/**
 * Handles requests to retrieve all genres.
 */
@Component
public class GenreGetAllHandler extends GenreBaseHandler<GenreGetAllQuery, List<GenreDto>> {
    private final GenreMapper genreMapper;
    private final GenreRepository genreRepository;

    /**
     * Creates a GenreGetAllHandler.
     *
     * @param genreMapper genre mapper
     * @param genreRepository genre repository
     */
    public GenreGetAllHandler(GenreMapper genreMapper, GenreRepository genreRepository) {
        super();
        this.genreMapper = genreMapper;
        this.genreRepository = genreRepository;
    }

    /**
     * Retrieves all genres.
     *
     * @param request query object
     * @return list of genre DTOs
     */
    @Override
    public List<GenreDto> execute(GenreGetAllQuery request) {
        return genreRepository.findAll().stream()
            .map(genreMapper::toDto)
            .toList();
    }
}
