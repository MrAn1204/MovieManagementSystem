package com.mms.mms_api.business.query;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class BaseGetByIdQuery {
    private UUID id;
}
