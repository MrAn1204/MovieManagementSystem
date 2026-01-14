package com.mms.mms_api.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class InvoiceDto {
    private UUID id;

    private int totalMoney;

    private int addScore;

    private int useScore;

    private double discount;

    private List<UUID> tickets;
}
