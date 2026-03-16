package com.mms.mms_api.dto.movie;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LanguageDto extends BaseDto {
    private String name;

    private String nativeName;
}
