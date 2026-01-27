package com.mms.mms_api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TalentDto extends BaseDto {
    private String name;

    private String profile;
}
