package com.mms.mms_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.business.service.InvoiceService;
import com.mms.mms_api.dto.InvoiceDto;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceCreateCommand command) {
        InvoiceDto invoiceDto = invoiceService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDto);
    }
}
