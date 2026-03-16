package com.mms.mms_api.business.handler.genre;
import com.mms.mms_api.util.mapper.GenreMapper;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.dto.movie.GenreDto;

import java.util.List;
import com.mms.mms_api.business.query.genre.GenreGetAllQuery;

public class GenreGetAllHandler extends GenreBaseHandler<GenreGetAllQuery, List<GenreDto>> {
    private final GenreMapper genreMapper;
    private final GenreRepository genreRepository;

    public GenreGetAllHandler(GenreGetAllQuery request, GenreMapper genreMapper, GenreRepository genreRepository) {
        super(request);
        this.genreMapper = genreMapper;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<GenreDto> execute() {
        return genreRepository.findAll().stream()
            .map(genreMapper::toDto)
            .toList();
    }
}
