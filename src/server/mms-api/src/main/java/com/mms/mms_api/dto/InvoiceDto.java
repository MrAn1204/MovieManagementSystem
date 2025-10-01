package com.mms.mms_api.dto;

import java.util.List;

import lombok.Data;

@Data
public class InvoiceDto {
    private int totalMoney;

    private int addScore;

    private int useScore;

    private double discount;

    private List<TicketDto> tickets;
}
