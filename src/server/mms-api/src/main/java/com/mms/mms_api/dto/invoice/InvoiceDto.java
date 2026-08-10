package com.mms.mms_api.dto.invoice;

import com.mms.mms_api.dto.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DTO representing invoice information and related tickets.
 *
 * Maps from {@link com.mms.mms_api.model.Invoice Invoice}.
 * Includes associated tickets.
 *
 * @see com.mms.mms_api.model.Invoice Invoice
 * @see com.mms.mms_api.dto.ticket.TicketDto TicketDto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InvoiceDto extends BaseDto {
    private String name;

    private int totalMoney;

    private int addScore;

    private int useScore;

    private double discount;
}
