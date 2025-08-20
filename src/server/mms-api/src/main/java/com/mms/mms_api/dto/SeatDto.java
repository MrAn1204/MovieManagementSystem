package com.mms.mms_api.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class SeatDto {
    private UUID id;

    private int seatColumn;

    private int seatRow;

    private String seatType;

    private String name;

    private String roomName;
}
