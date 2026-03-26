package com.mms.mms_api.business.command.promotion;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class PromotionUpdateCommand extends BaseUpdateCommand {
    @NotNull(message = "{promotion.title.required}")
    private String title;

    @NotNull(message = "{promotion.startDate.required}")
    private LocalDate startDate;

    @NotNull(message = "{promotion.endDate.required}")
    private LocalDate endDate;

    private String description;

    private MultipartFile image;

    @NotNull(message = "{promotion.discount.required}")
    @Range(min = 5, max = 100, message = "{promotion.discount.invalid}")
    private double discount;
}
