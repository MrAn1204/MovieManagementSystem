package com.mms.mms_api.dto.room;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomSummaryDto extends BaseDto {
    private int rowLength;

    private int columnLength;

    private String name;
}
