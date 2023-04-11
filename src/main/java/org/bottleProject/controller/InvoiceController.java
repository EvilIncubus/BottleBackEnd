package org.bottleProject.controller;

import org.bottleProject.entity.Order;
import org.bottleProject.service.InvoiceFileOperationService;
import org.bottleProject.service.InvoiceFileOperationsFactory;
import org.bottleProject.service.InvoicingService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/rest/api/customer/invoice")
public class InvoiceController {
    private final InvoicingService invoicingService;

    private final InvoiceFileOperationService invoiceFileOperationService;

    public InvoiceController( InvoicingService invoicingService, InvoiceFileOperationService invoiceFileOperationService) {
        this.invoicingService = invoicingService;
        this.invoiceFileOperationService = invoiceFileOperationService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/generateInvoice/{saveType}")
    public ResponseEntity<String> createInvoiceByOrder(@RequestBody Order order) {
        try {
            invoicingService.invoicing(order);
            return new ResponseEntity<>("invoice was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error creating invoice", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/downloadInvoiceByOrderId/{downloadType}/{customerId}/{orderId}")
    public ResponseEntity<Resource> download(@PathVariable Long customerId, @PathVariable Long orderId) throws IOException {
        File foundFile = invoiceFileOperationService.getFile(customerId, orderId);
        ByteArrayInputStream resource = new ByteArrayInputStream(Files.readAllBytes(Paths.get(foundFile.getAbsolutePath())));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + foundFile.getName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(foundFile.length())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(resource));
    }

}

