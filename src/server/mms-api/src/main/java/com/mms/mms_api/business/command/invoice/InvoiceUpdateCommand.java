package com.mms.mms_api.business.command.invoice;

import com.mms.mms_api.business.command.BaseUpdateCommand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class InvoiceUpdateCommand extends BaseUpdateCommand {
    private int totalMoney;

    private int addScore;

    private int useScore;

    private double discount;

    private List<UUID> ticketIds;
}