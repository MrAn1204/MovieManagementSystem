package com.mms.mms_api.dto.room;

import java.util.List;

import com.mms.mms_api.dto.seat.SeatDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomDetailDto extends RoomDto {
    private List<SeatDto> seats;
}
