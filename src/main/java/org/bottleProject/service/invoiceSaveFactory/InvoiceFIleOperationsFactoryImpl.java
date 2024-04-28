package org.bottleProject.service.invoiceSaveFactory;

import org.bottleProject.dao.InvoiceDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.service.GoogleApiService;
import org.bottleProject.service.InvoiceFileOperationsFactory;
import org.bottleProject.service.OperationsWithFile;
import org.springframework.stereotype.Service;

@Service
public class InvoiceFIleOperationsFactoryImpl implements InvoiceFileOperationsFactory {

    private final UserDao userDao;
    private final InvoiceDao invoiceDao;
    private final GoogleApiService googleApiService;

    public InvoiceFIleOperationsFactoryImpl(UserDao userDao, InvoiceDao invoiceDao, GoogleApiService googleApiService) {
        this.userDao = userDao;
        this.invoiceDao = invoiceDao;
        this.googleApiService = googleApiService;
    }

    @Override
    public OperationsWithFile createConfiguration(String configType){
        if(configType == null){
            return null;
        } else if (configType.equalsIgnoreCase("LOCAL")) {
            return new LocalFileOperationServiceImpl(userDao, invoiceDao);
        } else if (configType.equalsIgnoreCase("DRIVE")) {
            return new DriveFileOperationServiceImpl(userDao, invoiceDao, googleApiService);
        }
        return null;
    }
}
