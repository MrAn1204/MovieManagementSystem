package com.mms.mms_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mms.mms_api.business.command.invoice.InvoiceCreateCommand;
import com.mms.mms_api.business.command.invoice.InvoiceDeleteCommand;
import com.mms.mms_api.business.command.invoice.InvoiceUpdateCommand;
import com.mms.mms_api.business.query.invoice.InvoiceGetAllQuery;
import com.mms.mms_api.business.query.invoice.InvoiceGetByIdQuery;
import com.mms.mms_api.business.service.InvoiceService;
import com.mms.mms_api.dto.InvoiceDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/invoices")
@AllArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/create")
    public ResponseEntity<InvoiceDto> create(@Valid @RequestBody InvoiceCreateCommand command) {
        InvoiceDto invoiceDto = invoiceService.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceDto);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAll() {
        List<InvoiceDto> invoices = invoiceService.handle(new InvoiceGetAllQuery());
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getById(@PathVariable UUID id) {
        InvoiceDto invoice = invoiceService.handle(new InvoiceGetByIdQuery(id));
        return ResponseEntity.ok(invoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> update(@PathVariable UUID id, @Valid @RequestBody InvoiceUpdateCommand command) {
        command.setId(id);
        InvoiceDto updatedInvoice = invoiceService.handle(command);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        invoiceService.handle(new InvoiceDeleteCommand(id));
        return ResponseEntity.noContent().build();
    }
}
