package com.mms.mms_api.dto.movie;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing a movie genre.
 *
 * Maps from {@link com.mms.mms_api.model.Genre Genre}.
 *
 * @see com.mms.mms_api.model.Genre Genre
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GenreDto extends BaseDto {
    private String name;

    private String description;
}
