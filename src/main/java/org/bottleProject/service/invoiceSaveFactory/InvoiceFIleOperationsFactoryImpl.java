package org.bottleProject.service.invoiceSaveFactory;

import org.bottleProject.service.InvoiceFileOperationsFactory;
import org.bottleProject.service.OperationsWithFile;
import org.springframework.stereotype.Service;

@Service
public class InvoiceFIleOperationsFactoryImpl implements InvoiceFileOperationsFactory {

    @Override
    public OperationsWithFile createConfiguration(String configType){
        if(configType == null){
            return null;
        } else if (configType.equalsIgnoreCase("LOCAL")) {
            return new LocalFileOperationServiceImpl();
        } else if (configType.equalsIgnoreCase("DRIVE")) {
            return new DriveFileOperationServiceImpl();
        }
        return null;
    }
}
