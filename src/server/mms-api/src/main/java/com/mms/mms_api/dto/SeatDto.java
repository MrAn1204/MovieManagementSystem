package com.mms.mms_api.dto;

import java.util.UUID;

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
