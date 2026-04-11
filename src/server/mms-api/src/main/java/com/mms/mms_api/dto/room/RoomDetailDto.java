package com.mms.mms_api.dto.room;

import java.util.List;

import com.mms.mms_api.dto.seat.SeatDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing room details including seat layout.
 *
 * Maps from {@link com.mms.mms_api.model.Room Room}.
 * Extends {@link RoomDto RoomDto} with detailed seat information.
 *
 * @see com.mms.mms_api.model.Room Room
 * @see RoomDto RoomDto
 * @see com.mms.mms_api.dto.seat.SeatDto SeatDto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomDetailDto extends RoomDto {
    private List<SeatDto> seats;
}
