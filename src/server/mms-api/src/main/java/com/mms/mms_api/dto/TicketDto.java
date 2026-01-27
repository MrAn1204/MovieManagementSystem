package com.mms.mms_api.dto;

import com.mms.mms_api.common.IdNameDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDto extends BaseDto {
    private String name;

    private ScheduleDto schedule;

    private IdNameDto seat;

    private IdNameDto promotion;

    private int price;

    private String username;

    private String phoneNumber;
}
