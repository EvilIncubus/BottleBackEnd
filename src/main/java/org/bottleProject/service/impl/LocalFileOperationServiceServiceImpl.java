package org.bottleProject.service.impl;

import org.bottleProject.entity.Configuration;
import org.bottleProject.service.LocalFileOperationService;

public class LocalFileOperationServiceServiceImpl extends OperationsWithFile {

//        FileManager fileManager = new FileManager();
//        File file;
//        try {
//            file = fileManager.getExcelFile(invoiceWrapper.getEmail(), "Invoice" + localDateTime.toLocalDate() + ".xlsx");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        String fileId = googleApiService.uploadFileIntoDrive("Invoice" + localDateTime.toLocalDate()+ ".xlsx", file);
//
//        invoice.setOrderId((int) order.getOrderId());
//        invoice.setFileId(fileId);
//        invoice.setFileName(invoiceWrapper.getEmail() + "\\Invoice" + localDateTime.toLocalDate() + ".xlsx");
//        invoiceDao.create(invoice);

    @Override
    void saveFile() {

    }

    @Override
    void getFile() {

    }
}
