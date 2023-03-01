package org.bottleProject.service.invoiceSaveFactory;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.entity.Configuration;
import org.bottleProject.entity.Order;

import java.io.File;

public abstract class OperationsWithFile {
    Configuration configuration;
    public abstract void saveFile(Workbook workbook, Configuration configuration, Order order);
    public abstract File getFile(Configuration configuration, long customerId, long orderId);
}
