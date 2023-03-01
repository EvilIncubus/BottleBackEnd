package org.bottleProject.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.entity.Order;

import java.io.File;

public interface InvoiceFileOperationService {

    void fileToSave(Workbook workbook, Order order);

    File getFile(long customerId, long orderId);

}
