package com.mms.mms_api.business.command.invoice;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class InvoiceUpdateCommand extends BaseUpdateCommand {
    @PositiveOrZero(message = "{invoice.addScore.invalid}")
    private int addScore;

    @PositiveOrZero(message = "{invoice.useScore.invalid}")
    private int useScore;

    @PositiveOrZero(message = "{invoice.discount.invalid}")
    private double discount;

    @NotEmpty(message = "{invoice.tickets.required}")
    private List<UUID> ticketIds;
}