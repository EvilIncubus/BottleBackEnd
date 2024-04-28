package org.bottleProject.controller;

import org.bottleProject.dto.InvoiceWrapper;
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
import java.util.logging.Logger;

@RestController
@RequestMapping("/rest/api/customer/invoice")
public class InvoiceController {
    private final InvoicingService invoicingService;

    private final InvoiceFileOperationService invoiceFileOperationService;
    private final static Logger logger = Logger.getLogger(InvoiceController.class.getName());


    public InvoiceController( InvoicingService invoicingService, InvoiceFileOperationService invoiceFileOperationService) {
        this.invoicingService = invoicingService;
        this.invoiceFileOperationService = invoiceFileOperationService;
    }

    @PreAuthorize("hasAnyAuthority('SHIPPER')")
    @PostMapping("/generateInvoice")
    public ResponseEntity<String> createInvoiceByOrder(@RequestBody InvoiceWrapper invoiceWrapper) {
        try {
            invoicingService.invoicing(invoiceWrapper);
            return new ResponseEntity<>("invoice was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>("error creating invoice", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('SHIPPER')")
    @GetMapping("/downloadInvoiceByOrderId")
    public ResponseEntity<Resource> download(@RequestParam Long orderId){
        File foundFile = invoiceFileOperationService.getFile(orderId);
        ByteArrayInputStream resource = null;
        try {
            resource = new ByteArrayInputStream(Files.readAllBytes(Paths.get(foundFile.getAbsolutePath())));
        } catch (IOException e) {
            logger.info(e.toString());
            throw new RuntimeException(e);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + foundFile.getName());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(foundFile.length())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(resource));
    }

}

