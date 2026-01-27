package com.mms.mms_api.dto;

import java.time.LocalDate;
import java.util.List;

import com.mms.mms_api.common.IdNameDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieDto extends BaseDto {

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