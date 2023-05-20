package org.bottleProject.service.invoiceSaveFactory;

import org.bottleProject.service.InvoiceFileOperationsFactory;
import org.springframework.stereotype.Service;

@Service
public class InvoiceFIleOperationsFactoryImpl implements InvoiceFileOperationsFactory {
    private OperationsWithFile operationsWithFile;

    @Override
    public OperationsWithFile createConfiguration(String configType){
        if(configType == null){
            return null;
        } else if (configType.equalsIgnoreCase("LOCAL")) {
            return operationsWithFile = new LocalFileOperationServiceImpl();
        } else if (configType.equalsIgnoreCase("DRIVE")) {
            return operationsWithFile = new DriveFileOperationServiceImpl();
        }
        return null;
    }
}
