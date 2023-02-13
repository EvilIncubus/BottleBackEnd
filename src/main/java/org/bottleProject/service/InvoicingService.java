package org.bottleProject.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.entity.Order;

import java.io.File;
import java.time.LocalDateTime;

public interface InvoicingService {

    Workbook prepareInvoice(Order order);

    Workbook invoicing(InvoiceWrapper invoiceDto, LocalDateTime localDateTime);

    File getInvoiceContents(long customerId, long orderId);
}
