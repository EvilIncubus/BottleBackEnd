package org.example.service;

import org.example.dto.InvoiceWrapper;
import org.example.util.FileManager;

public interface InvoicingService {

    FileManager invoicing(InvoiceWrapper invoiceDto);
}
