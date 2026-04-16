package com.mms.mms_api.business.command.invoice;

import com.mms.mms_api.business.command.BaseDeleteCommand;

import java.util.UUID;

/**
 * Command payload for deleting invoices.
 */
public class InvoiceDeleteCommand extends BaseDeleteCommand {
    public InvoiceDeleteCommand(UUID id) {
        super(id);
    }
}