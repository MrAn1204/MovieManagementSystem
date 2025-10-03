package com.mms.mms_api.business.command.promotion;

import com.mms.mms_api.business.command.BaseUpdateCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class PromotionUpdateCommand extends BaseUpdateCommand {
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String image;

    private int discount;
}
