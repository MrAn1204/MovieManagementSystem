package com.mms.mms_api.dto.promotion;

import java.time.LocalDate;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PromotionDto extends BaseDto {
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String image;

    private double discount;
}
