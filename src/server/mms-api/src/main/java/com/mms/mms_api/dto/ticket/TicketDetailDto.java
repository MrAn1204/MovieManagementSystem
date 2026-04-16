package com.mms.mms_api.dto.ticket;

import com.mms.mms_api.common.IdNameDto;
import com.mms.mms_api.dto.schedule.ScheduleDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing detailed ticket data.
 *
 * Maps from {@link com.mms.mms_api.model.Ticket Ticket}.
 * Extends {@link TicketDto TicketDto} with detailed schedule, seat, and promotion information.
 *
 * @see com.mms.mms_api.model.Ticket Ticket
 * @see TicketDto TicketDto
 * @see com.mms.mms_api.dto.schedule.ScheduleDto ScheduleDto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDetailDto extends TicketDto {
    private ScheduleDto schedule;

    private IdNameDto seat;

    private IdNameDto promotion;
}
