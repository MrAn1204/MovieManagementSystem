package com.mms.mms_api.dto.ticket;

import com.mms.mms_api.common.IdNameDto;
import com.mms.mms_api.dto.BaseDto;
import com.mms.mms_api.dto.user.UserSummaryDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing ticket summary information.
 *
 * Maps from {@link com.mms.mms_api.model.Ticket Ticket}.
 * Includes movie and user summary information.
 *
 * @see com.mms.mms_api.model.Ticket Ticket
 * @see com.mms.mms_api.dto.user.UserSummaryDto UserSummaryDto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDto extends BaseDto {
    private String name;

    private IdNameDto movie;

    private UserSummaryDto user;

    private int price;

    private boolean paid;
}
