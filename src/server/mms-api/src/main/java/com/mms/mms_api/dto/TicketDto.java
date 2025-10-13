package com.mms.mms_api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class TicketDto {
    UUID id;

    ScheduleDto schedule;

    SeatDto seat;

    PromotionDto promotion;

    int price;
}
