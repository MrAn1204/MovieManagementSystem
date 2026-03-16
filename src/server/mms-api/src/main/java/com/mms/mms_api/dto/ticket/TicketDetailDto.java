package com.mms.mms_api.dto.ticket;

import java.time.LocalDateTime;

import com.mms.mms_api.common.IdNameDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDetailDto extends TicketDto {
    private LocalDateTime showTime;

    private IdNameDto room;

    private IdNameDto seat;

    private IdNameDto promotion;

    private int price;
}
