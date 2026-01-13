package com.mms.mms_api.common;

import java.util.UUID;

import lombok.Data;

@Data
public class IdNameDto {
    private UUID id;

    private String name;
}
