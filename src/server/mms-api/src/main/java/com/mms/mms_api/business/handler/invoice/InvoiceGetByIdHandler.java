package com.mms.mms_api.business.handler.invoice;

import com.mms.mms_api.util.CurrentUserHelper;
import org.springframework.stereotype.Component;

import com.mms.mms_api.business.query.invoice.InvoiceGetByIdQuery;
import com.mms.mms_api.exception.InvalidInputException;
import com.mms.mms_api.model.Invoice;
import com.mms.mms_api.util.mapper.InvoiceMapper;
import com.mms.mms_api.data.InvoiceRepository;
import com.mms.mms_api.dto.AuditDto;
import com.mms.mms_api.dto.invoice.InvoiceDetailDto;

/**
 * Handles requests to retrieve invoice by id.
 */
@Component
public class InvoiceGetByIdHandler extends InvoiceBaseHandler<InvoiceGetByIdQuery, InvoiceDetailDto> {

    private final CurrentUserHelper currentUser;

    /**
     * Creates an InvoiceGetByIdHandler.
     *
     * @param invoiceMapper invoice mapper
     * @param invoiceRepository invoice repository
     */
    public InvoiceGetByIdHandler(InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository, CurrentUserHelper currentUserHelper) {
        super(invoiceMapper, invoiceRepository);
        this.currentUser = currentUserHelper;
    }

    /**
     * Retrieves an invoice by its identifier.
     *
     * @param request query containing the target invoice id
     * @return invoice DTO
     * @throws com.mms.mms_api.exception.InvalidInputException when the invoice does not exist
     */
    @Override
    public InvoiceDetailDto execute(InvoiceGetByIdQuery request) {
        Invoice invoice = invoiceRepository.findById(request.getId())
                .orElseThrow(() -> new InvalidInputException("invoice.notFound"));

        InvoiceDetailDto dto = invoiceMapper.toDetailDto(invoice);

        if (currentUser.isAdmin()) {
            dto.setAudit(new AuditDto(invoice));
        }

        return dto;
    }
}