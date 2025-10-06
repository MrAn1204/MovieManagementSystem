package com.mms.mms_api.business.command.promotion;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    private String image;

    private double discount;

    private List<UUID> ticketIds;
}
