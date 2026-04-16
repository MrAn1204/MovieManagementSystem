package com.mms.mms_api.dto.movie;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing a production studio.
 *
 * Maps from {@link com.mms.mms_api.model.Studio Studio}.
 *
 * @see com.mms.mms_api.model.Studio Studio
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudioDto extends BaseDto {
    private String name;

    private String profile;
}
