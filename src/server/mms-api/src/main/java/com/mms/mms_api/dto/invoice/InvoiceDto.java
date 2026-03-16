package com.mms.mms_api.dto.invoice;

import java.util.List;

import com.mms.mms_api.dto.BaseDto;
import com.mms.mms_api.dto.ticket.TicketDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceDto extends BaseDto {
    private String name;

    private int totalMoney;

    private int addScore;

    private int useScore;

    private double discount;

    private List<TicketDto> tickets;
}
