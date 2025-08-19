package com.mms.mms_api.dto;

import java.util.List;

import lombok.Data;

@Data
public class RoomDto {
    private int seatQuantity;

    private String name;

    private List<SeatDto> seats;
}
