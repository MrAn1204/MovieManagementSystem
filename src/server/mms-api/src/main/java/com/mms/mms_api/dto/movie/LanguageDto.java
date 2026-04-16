package com.mms.mms_api.dto.movie;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing a movie language.
 *
 * Maps from {@link com.mms.mms_api.model.Language Language}.
 *
 * @see com.mms.mms_api.model.Language Language
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LanguageDto extends BaseDto {
    private String name;

    private String nativeName;
}
