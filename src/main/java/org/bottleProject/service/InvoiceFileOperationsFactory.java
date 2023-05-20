package org.bottleProject.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.entity.Order;
import org.bottleProject.service.invoiceSaveFactory.OperationsWithFile;

import java.io.File;

public interface InvoiceFileOperationsFactory {

    OperationsWithFile createConfiguration(String configType);
}
