package com.mms.mms_api.data;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mms.mms_api.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

}
