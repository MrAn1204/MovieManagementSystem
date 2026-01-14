package com.mms.mms_api.dto;

import java.util.UUID;

import com.mms.mms_api.common.IdNameDto;

import lombok.Data;

@Data
public class TicketDto {
    private UUID id;

    private ScheduleDto schedule;

    private IdNameDto seat;

    private IdNameDto promotion;

    private int price;

    private String username;

    private String phoneNumber;
}
