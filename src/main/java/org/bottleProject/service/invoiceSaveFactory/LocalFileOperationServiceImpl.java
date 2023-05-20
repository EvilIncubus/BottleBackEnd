package org.bottleProject.service.invoiceSaveFactory;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dao.InvoiceDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.Configuration;
import org.bottleProject.entity.Invoice;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.User;
import org.bottleProject.util.FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class LocalFileOperationServiceImpl extends OperationsWithFile {

    private UserDao userDao;
    private InvoiceDao invoiceDao;

    public LocalFileOperationServiceImpl(UserDao userDao, InvoiceDao invoiceDao) {
        this.userDao = userDao;
        this.invoiceDao = invoiceDao;
    }

    public LocalFileOperationServiceImpl() {

    }

    @Override
    public void saveFile(Workbook workbook, Configuration configuration, Order order) {
        User user = userDao.findById((long) order.getProfileId());
        LocalDateTime localDateTime = LocalDateTime.now();
        Path filePath = Path.of(user.getEmail() + "Invoice" + localDateTime.toLocalDate() + "xlsx");
        FileManager fileManager = new FileManager(filePath);
        try {
            fileManager.writeExcelFileLocal(workbook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Invoice invoice = new Invoice();
        invoice.setOrderId((int) order.getOrderId());
        invoice.setFileId("Empty");
        invoice.setFileName(user.getEmail() + "\\Invoice" + localDateTime.toLocalDate() + ".xlsx");
        invoiceDao.create(invoice);
        System.out.println("file saved");
    }

    @Override
    public File getFile(Configuration configuration, long customerId, long orderId) {
        Invoice invoice = invoiceDao.findByOrderId(orderId);
        User customer = userDao.findById(customerId);
        FileManager fileManager = new FileManager();
        File file = null;
        try {
            file = fileManager.getExcelFile(customer.getEmail(), invoice.getFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("file get local");
        return file;
    }
}
