package com.mms.mms_api.dto.schedule;

import java.util.List;

import com.mms.mms_api.dto.seat.SeatDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleDetailDto extends ScheduleDto {
    private List<SeatDto> seats;
}
