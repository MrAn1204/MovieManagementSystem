package com.mms.mms_api.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mms.mms_api.model.Invoice;

/**
 * Repository for invoice persistence operations.
 */
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

}
