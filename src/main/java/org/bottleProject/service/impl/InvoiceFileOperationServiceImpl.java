package org.bottleProject.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dao.ConfigurationDao;
import org.bottleProject.entity.Configuration;
import org.bottleProject.entity.Order;
import org.bottleProject.service.InvoiceFileOperationService;
import org.bottleProject.service.InvoiceFileOperationsFactory;
import org.bottleProject.service.invoiceSaveFactory.OperationsWithFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class InvoiceFileOperationServiceImpl implements InvoiceFileOperationService {

    private final ConfigurationDao configurationDao;
    private OperationsWithFile operationsWithFile;
    private Configuration configuration;
    private final InvoiceFileOperationsFactory invoiceFileOperationsFactory;

    public InvoiceFileOperationServiceImpl(ConfigurationDao configurationDao, InvoiceFileOperationsFactory invoiceFileOperationsFactory) {
        this.configurationDao = configurationDao;
        this.invoiceFileOperationsFactory = invoiceFileOperationsFactory;
    }
//todo: param for requset configuration from bd
    @Override
    public void fileToSave(Workbook workbook, Order order){
        String configType = configurationDao.getEnableStatus();
        configuration = configurationDao.findConfigByType(configType);
        operationsWithFile = invoiceFileOperationsFactory.createConfiguration(configType);
        Objects.requireNonNull(operationsWithFile).saveFile(workbook, configuration, order);
    }

    @Override
    public File getFile(long customerId, long orderId) {
        String configType = configurationDao.getEnableStatus();
        configuration = configurationDao.findConfigByType(configType);
        operationsWithFile = invoiceFileOperationsFactory.createConfiguration(configType);
        return Objects.requireNonNull(operationsWithFile).getFile(configuration, customerId, orderId);
    }

}
