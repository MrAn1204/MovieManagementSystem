package com.mms.mms_api.business.commands;

import java.util.UUID;

import lombok.Data;

@Data
public abstract class BaseDeleteCommand {
    private UUID id;
}
