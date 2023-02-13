package org.bottleProject.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.entity.Configuration;

public interface InvoiceFileOperationsService {

    Configuration findConfigurationForSavingFile(String saveType);

    String fileSaveSelect(String saveType, Workbook workbook);

}
