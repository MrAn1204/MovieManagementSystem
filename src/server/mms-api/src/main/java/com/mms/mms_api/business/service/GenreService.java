package com.mms.mms_api.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mms.mms_api.business.handler.genre.GenreGetAllHandler;
import com.mms.mms_api.business.query.genre.GenreGetAllQuery;
import com.mms.mms_api.data.GenreRepository;
import com.mms.mms_api.dto.GenreDto;
import com.mms.mms_api.util.mapper.GenreMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    
    private final GenreMapper genreMapper;

    public List<GenreDto> handle(GenreGetAllQuery request) {
        GenreGetAllHandler handler = new GenreGetAllHandler(request, genreMapper, genreRepository);
        return handler.execute();
    }
}
