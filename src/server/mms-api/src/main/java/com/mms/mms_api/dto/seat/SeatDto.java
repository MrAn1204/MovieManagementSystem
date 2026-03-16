package com.mms.mms_api.dto.seat;

import java.util.UUID;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SeatDto extends BaseDto {
    private int seatColumn;

    private int seatRow;

    private String seatType;

    private String name;

    private UUID linkedSeatId;

    private Boolean reserved;
}
