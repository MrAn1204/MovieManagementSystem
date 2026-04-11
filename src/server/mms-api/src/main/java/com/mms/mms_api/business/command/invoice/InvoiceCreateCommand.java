package com.mms.mms_api.business.command.invoice;

import com.mms.mms_api.business.command.BaseCreateCommand;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

/**
 * Command payload for creating invoices.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class InvoiceCreateCommand extends BaseCreateCommand {
    @PositiveOrZero(message = "{invoice.addScore.invalid}")
    private int addScore;

    @PositiveOrZero(message = "{invoice.useScore.invalid}")
    private int useScore;

    @PositiveOrZero(message = "{invoice.discount.invalid}")
    private double discount;

    @NotEmpty(message = "{invoice.tickets.required}")
    private List<UUID> ticketIds;

    @NotNull(message = "{user.required}")
    @NonNull
    private UUID userId;
}