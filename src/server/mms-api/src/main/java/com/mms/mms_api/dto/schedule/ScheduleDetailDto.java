package com.mms.mms_api.dto.schedule;

import java.time.LocalDateTime;
import java.util.List;

import com.mms.mms_api.common.IdNameDto;
import com.mms.mms_api.dto.BaseDto;
import com.mms.mms_api.dto.seat.SeatDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScheduleDetailDto extends BaseDto {
    private String name;

    private LocalDateTime showTime;

    private IdNameDto movie;

    private IdNameDto room;

    private List<SeatDto> seats;
}
