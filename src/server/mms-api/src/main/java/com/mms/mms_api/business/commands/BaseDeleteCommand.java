package com.mms.mms_api.business.commands;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class BaseDeleteCommand {
    private UUID id;
}
