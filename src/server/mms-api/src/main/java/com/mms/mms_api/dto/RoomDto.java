package com.mms.mms_api.dto;

import java.util.List;

import com.mms.mms_api.common.IdNameDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoomDto extends BaseDto {
    private int rowLength;

    private int columnLength;

    private String name;

    private List<IdNameDto> seats;
}
