package org.bottleProject.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dao.ConfigurationDao;
import org.bottleProject.dao.InvoiceDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.DriveConfiguration;
import org.bottleProject.entity.Order;
import org.bottleProject.service.InvoiceFileOperationService;
import org.bottleProject.service.InvoiceFileOperationsFactory;
import org.bottleProject.service.OperationsWithFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class InvoiceFileOperationServiceImpl implements InvoiceFileOperationService {

    private final ConfigurationDao configurationDao;
    private OperationsWithFile operationsWithFile;
    private DriveConfiguration configuration;
    private final InvoiceFileOperationsFactory invoiceFileOperationsFactory;
    private UserDao userDao;
    private InvoiceDao invoiceDao;

    public InvoiceFileOperationServiceImpl(ConfigurationDao configurationDao, InvoiceFileOperationsFactory invoiceFileOperationsFactory, UserDao userDao, InvoiceDao invoiceDao) {
        this.configurationDao = configurationDao;
        this.invoiceFileOperationsFactory = invoiceFileOperationsFactory;
        this.userDao = userDao;
        this.invoiceDao = invoiceDao;
    }

    @Override
    public void fileToSave(Workbook workbook, Order order){
        String configType = configurationDao.getEnableStatus();
//        configuration = configurationDao.findConfigByType(configType);
        operationsWithFile = invoiceFileOperationsFactory.createConfiguration(configType);

        Objects.requireNonNull(operationsWithFile).saveFile(workbook, order);
    }

    @Override
    public File getFile(long orderId) {
        String configType = configurationDao.getEnableStatus();
//        configuration = configurationDao.findConfigByType(configType);
        operationsWithFile = invoiceFileOperationsFactory.createConfiguration(configType);
        return Objects.requireNonNull(operationsWithFile).getFile( orderId);
    }

}
