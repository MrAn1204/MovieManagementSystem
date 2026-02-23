package com.mms.mms_api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TicketDetailDto extends TicketDto {
    private String username;

    private String phoneNumber;
}
