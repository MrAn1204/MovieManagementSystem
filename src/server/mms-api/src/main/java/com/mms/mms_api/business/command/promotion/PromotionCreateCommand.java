package com.mms.mms_api.business.command.promotion;

import com.mms.mms_api.business.command.BaseCreateCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class PromotionCreateCommand extends BaseCreateCommand {
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String image;

    private int discount;

    private List<UUID> ticketIds;
}
