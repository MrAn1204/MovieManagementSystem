package com.mms.mms_api.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomDetailDto extends RoomDto {
    private List<SeatDto> seats;
}
