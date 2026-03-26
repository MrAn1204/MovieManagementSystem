package com.mms.mms_api.dto.ticket;

import com.mms.mms_api.common.IdNameDto;
import com.mms.mms_api.dto.schedule.ScheduleDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDetailDto extends TicketDto {
    private ScheduleDto schedule;

    private IdNameDto seat;

    private IdNameDto promotion;
}
