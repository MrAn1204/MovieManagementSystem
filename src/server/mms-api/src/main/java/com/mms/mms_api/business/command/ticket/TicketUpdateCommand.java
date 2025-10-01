package com.mms.mms_api.business.command.ticket;

import java.util.UUID;

import com.mms.mms_api.business.command.BaseUpdateCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TicketUpdateCommand extends BaseUpdateCommand {
    private int price;

    private UUID scheduleId;

    private UUID seatId;
}