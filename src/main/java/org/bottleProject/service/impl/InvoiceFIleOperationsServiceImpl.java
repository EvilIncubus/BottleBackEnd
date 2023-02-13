package org.bottleProject.service.impl;

import org.apache.poi.ss.usermodel.Workbook;

class InvoiceFIleOperationsServiceImpl{

    public OperationsWithFile fileSaveSelect(String saveType, Workbook workbook){

        if(saveType == null){
            return null;
        } else if (saveType.equalsIgnoreCase("DRIVE_SAVE")) {
            return new LocalFileOperationServiceServiceImpl();
        } else if (saveType.equalsIgnoreCase("SAVE_LOCAL")) {
            return new DriveFileOperationServiceServiceImpl();
        }
        return null;
    }

}
