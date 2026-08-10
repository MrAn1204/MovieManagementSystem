package com.mms.mms_api.dto.invoice;

import java.util.List;

import com.mms.mms_api.dto.ticket.TicketDto;
import com.mms.mms_api.dto.user.UserSummaryDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceDetailDto extends InvoiceDto {
    private List<TicketDto> tickets;

    private UserSummaryDto user;
}
