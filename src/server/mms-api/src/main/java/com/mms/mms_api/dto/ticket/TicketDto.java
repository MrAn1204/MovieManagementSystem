package com.mms.mms_api.dto.ticket;

import com.mms.mms_api.common.IdNameDto;
import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDto extends BaseDto {
    private String name;

    private IdNameDto movie;

    private String username;

    private String phoneNumber;
}
