package com.mms.mms_api.dto.seat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.room.RoomSummaryDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SeatDetailDto extends SeatDto {
    private RoomSummaryDto room;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AuditDto audit;
}
