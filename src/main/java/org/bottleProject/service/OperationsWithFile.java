package org.bottleProject.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.entity.DriveConfiguration;
import org.bottleProject.entity.Order;

import java.io.File;

public interface OperationsWithFile {
    void saveFile(Workbook workbook, DriveConfiguration configuration, Order order);
    File getFile(DriveConfiguration configuration, long customerId, long orderId);
}
