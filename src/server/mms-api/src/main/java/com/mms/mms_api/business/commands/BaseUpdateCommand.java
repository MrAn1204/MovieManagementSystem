package com.mms.mms_api.business.commands;

import java.util.UUID;

import lombok.Data;

@Data
public abstract class BaseUpdateCommand {
    private UUID id;
}
