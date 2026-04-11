package com.mms.mms_api.dto.room;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing room summary information.
 *
 * Maps from {@link com.mms.mms_api.model.Room Room}.
 *
 * @see com.mms.mms_api.model.Room Room
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoomDto extends BaseDto {
    private int rowLength;

    private int columnLength;

    private String name;

    private int maxCapacity;

    private int currentCapacity;
}
