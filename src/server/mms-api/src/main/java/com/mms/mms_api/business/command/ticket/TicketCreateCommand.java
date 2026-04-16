package com.mms.mms_api.business.command.ticket;

import java.util.List;
import java.util.UUID;

import org.springframework.lang.NonNull;

import com.mms.mms_api.business.command.BaseCreateCommand;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Command payload for creating tickets.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TicketCreateCommand extends BaseCreateCommand {
    @NotNull(message = "{ticket.schedule.required}")
    @NonNull
    private UUID scheduleId;

    @NotEmpty(message = "{ticket.seat.required}")
    @NonNull
    private List<@NotNull(message = "{ticket.seats.invalid}") UUID> seatIds;

    private UUID promotionId;

    @NotNull(message = "{user.required}")
    @NonNull
    private UUID userId;
}