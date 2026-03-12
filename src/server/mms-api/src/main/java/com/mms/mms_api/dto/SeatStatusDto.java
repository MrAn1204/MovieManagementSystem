package com.mms.mms_api.dto;

import java.util.UUID;

import com.mms.mms_api.model.SeatType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatStatusDto {
    private UUID id;

    private SeatType seatType;

    private boolean reserved;
}
