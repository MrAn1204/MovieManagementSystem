package com.mms.mms_api.business.command.ticket;

import com.mms.mms_api.business.command.BaseUpdateCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TicketUpdateCommand extends BaseUpdateCommand {
    private int price;

    private Long scheduleId;

    private Long seatId;
}