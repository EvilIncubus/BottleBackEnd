package org.bottleProject.service;

import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.util.FileManager;

public interface InvoicingService {

    FileManager invoicing(InvoiceWrapper invoiceDto);
}
