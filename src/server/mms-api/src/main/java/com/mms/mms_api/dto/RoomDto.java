package com.mms.mms_api.dto;

import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class RoomDto {
    private UUID id;

    private int seatQuantity;

    private String name;

    private List<SeatDto> seats;
}
