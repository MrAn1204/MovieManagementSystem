package com.mms.mms_api.business.query.invoice;

import com.mms.mms_api.business.query.BaseGetByIdQuery;
import java.util.UUID;

/**
 * Query payload for retrieving invoice by id.
 */
public class InvoiceGetByIdQuery extends BaseGetByIdQuery {
    public InvoiceGetByIdQuery(UUID id) {
        super(id);
    }
}