package com.mms.mms_api.dto;

import java.util.List;
import java.util.UUID;

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

    private List<UUID> tickets;
}
