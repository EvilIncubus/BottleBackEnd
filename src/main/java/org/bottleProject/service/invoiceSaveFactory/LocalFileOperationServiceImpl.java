package org.bottleProject.service.invoiceSaveFactory;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dao.InvoiceDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.DriveConfiguration;
import org.bottleProject.entity.Invoice;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.User;
import org.bottleProject.service.OperationsWithFile;
import org.bottleProject.util.FileManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
public class LocalFileOperationServiceImpl implements OperationsWithFile {

    private final UserDao userDao;
    private final InvoiceDao invoiceDao;

    public LocalFileOperationServiceImpl(UserDao userDao, InvoiceDao invoiceDao) {
        this.userDao = userDao;
        this.invoiceDao = invoiceDao;
    }

    @Override
    public void saveFile(Workbook workbook, Order order) {
        User user = userDao.findById((long) order.getProfileId());
        LocalDateTime localDateTime = LocalDateTime.now();
        FileManager fileManager = new FileManager(user.getEmail(),"Invoice" + localDateTime.toLocalDate(), "xlsx");
        fileManager.writeExcelFile(workbook);
        Invoice invoice = new Invoice();
        invoice.setOrderId((int) order.getOrderId());
        invoice.setFileId("Empty");
        invoice.setFileName(user.getEmail() + "\\Invoice" + localDateTime.toLocalDate() + ".xlsx");
        invoiceDao.create(invoice);
        System.out.println("file saved");
    }

    @Override
    public File getFile(long orderId) {
        Invoice invoice = invoiceDao.findByOrderId(orderId);
        FileManager fileManager = new FileManager();
        File file;
        try {
            file = fileManager.getExcelFile(invoice.getFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("file get local");
        return file;
    }
}
