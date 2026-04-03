package com.mms.mms_api.dto.ticket;

import com.mms.mms_api.common.IdNameDto;
import com.mms.mms_api.dto.BaseDto;
import com.mms.mms_api.dto.user.UserSummaryDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDto extends BaseDto {
    private String name;

    private IdNameDto movie;

    private UserSummaryDto user;

    private int price;

    private boolean paid;
}
