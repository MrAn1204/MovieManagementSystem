package com.mms.mms_api.dto.seat;

import com.mms.mms_api.dto.AuditDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SeatDetailDto extends SeatDto {
    private AuditDto audit;
}
