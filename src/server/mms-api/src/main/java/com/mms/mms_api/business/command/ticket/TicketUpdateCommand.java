package com.mms.mms_api.business.command.ticket;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TicketUpdateCommand extends BaseUpdateCommand {
    private int price;

    @NotNull(message = "{ticket.schedule.required}")
    private UUID scheduleId;

    @NotNull(message = "{ticket.seat.required}")
    private UUID seatId;

    private UUID invoiceId;

    private UUID promotionId;
}