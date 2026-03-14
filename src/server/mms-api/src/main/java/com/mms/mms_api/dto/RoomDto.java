package com.mms.mms_api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomDto extends BaseDto {
    private int rowLength;

    private int columnLength;

    private String name;

    private int maxCapacity;

    private int currentCapacity;
}
