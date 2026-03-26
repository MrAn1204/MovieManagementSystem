package com.mms.mms_api.business.command.promotion;

import com.mms.mms_api.business.command.BaseCreateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class PromotionCreateCommand extends BaseCreateCommand {
    @NotNull(message = "{promotion.title.required}")
    private String title;

    @NotNull(message = "{promotion.startDate.required}")
    private LocalDate startDate;

    @NotNull(message = "{promotion.endDate.required}")
    private LocalDate endDate;

    private String description;

    private MultipartFile image;

    private double discount;

    private List<UUID> ticketIds;
}
