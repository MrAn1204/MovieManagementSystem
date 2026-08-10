package com.mms.mms_api.dto.schedule;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.seat.SeatDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing detailed schedule data and seat availability.
 *
 * Maps from {@link com.mms.mms_api.model.Schedule Schedule}.
 * Extends {@link ScheduleDto ScheduleDto} with detailed seat layout information.
 *
 * @see com.mms.mms_api.model.Schedule Schedule
 * @see ScheduleDto ScheduleDto
 * @see com.mms.mms_api.dto.seat.SeatDto SeatDto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleDetailDto extends ScheduleDto {
    private List<SeatDto> seats;

    private int rowLength;

    private int columnLength;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AuditDto audit;
}
