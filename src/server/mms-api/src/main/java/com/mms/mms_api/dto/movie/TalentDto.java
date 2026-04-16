package com.mms.mms_api.dto.movie;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing a talent.
 *
 * Maps from {@link com.mms.mms_api.model.Talent Talent}.
 *
 * @see com.mms.mms_api.model.Talent Talent
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TalentDto extends BaseDto {
    private String name;

    private String profile;
}
