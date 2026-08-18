package com.mms.mms_api.dto.promotion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mms.mms_api.dto.AuditDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PromotionDetailDto extends PromotionDto {
    private String description;

    private String image;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AuditDto audit;
}
