package com.mms.mms_api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.mms.mms_api.common.IdNameDto;

import lombok.Data;

@Data
public class MovieDto {
    private UUID id;

    private String name;

    private LocalDate releaseDate;

    private int duration;

    private String content;

    private String thumbnail;

    private double rating;

    private List<IdNameDto> genres;

    private List<IdNameDto> studios;

    private List<IdNameDto> talents;

    private IdNameDto language;
}