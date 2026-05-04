package com.mms.mms_api.dto.movie;

import java.time.LocalDate;
import java.util.List;

import com.mms.mms_api.common.IdNameDto;
import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing movie information for API responses.
 *
 * Maps from {@link com.mms.mms_api.model.Movie Movie}.
 * Includes genre, studio, talent, and language information.
 *
 * @see com.mms.mms_api.model.Movie Movie
 * @see GenreDto GenreDto
 * @see StudioDto StudioDto
 * @see TalentDto TalentDto
 * @see LanguageDto LanguageDto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MovieDto extends BaseDto {

    private String name;

    private LocalDate releaseDate;

    private int duration;

    private double rating;

    private List<IdNameDto> genres;

    private List<IdNameDto> studios;

    private List<IdNameDto> talents;

    private IdNameDto language;
}