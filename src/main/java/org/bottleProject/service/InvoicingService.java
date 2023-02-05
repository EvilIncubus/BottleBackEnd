package org.bottleProject.service;

import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.entity.Order;

import java.io.File;
import java.time.LocalDateTime;

public interface InvoicingService {

    public void prepareInvoice(Order order);

    LocalDateTime invoicing(InvoiceWrapper invoiceDto);

    File getInvoiceContents(long customerId, long orderId);
}
