package com.mms.mms_api.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class PromotionDto {
    private UUID id;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String image;

    private int discount;

    private List<TicketDto> tickets;
}
