package org.bottleProject.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.entity.Order;

import java.io.File;
import java.time.LocalDateTime;

public interface InvoicingService {

    void invoicing(Order order);

}
