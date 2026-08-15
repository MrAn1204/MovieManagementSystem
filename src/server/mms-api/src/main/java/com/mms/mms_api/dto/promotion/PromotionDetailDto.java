package com.mms.mms_api.dto.promotion;

import com.mms.mms_api.dto.AuditDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PromotionDetailDto extends PromotionDto {
    private String description;

    private String image;

    private AuditDto audit;
}
