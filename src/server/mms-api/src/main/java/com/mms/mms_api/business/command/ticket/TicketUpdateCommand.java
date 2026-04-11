package com.mms.mms_api.business.command.ticket;

import java.util.UUID;

import org.springframework.lang.NonNull;

import com.mms.mms_api.business.command.BaseUpdateCommand;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Command payload for updating tickets.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TicketUpdateCommand extends BaseUpdateCommand {
    @NotNull(message = "{ticket.schedule.required}")
    @NonNull
    private UUID scheduleId;

    @NotNull(message = "{ticket.seat.required}")
    @NonNull
    private UUID seatId;

    private UUID promotionId;
}