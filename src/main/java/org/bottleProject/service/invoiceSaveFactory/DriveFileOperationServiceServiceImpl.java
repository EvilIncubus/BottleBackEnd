package org.bottleProject.service.invoiceSaveFactory;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dao.InvoiceDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.Configuration;
import org.bottleProject.entity.Invoice;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.User;
import org.bottleProject.service.GoogleApiService;
import org.bottleProject.util.FileManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
public class DriveFileOperationServiceServiceImpl extends OperationsWithFile {

    private UserDao userDao;
    private InvoiceDao invoiceDao;
    private GoogleApiService googleApiService;

    public DriveFileOperationServiceServiceImpl(UserDao userDao, InvoiceDao invoiceDao, GoogleApiService googleApiService) {
        this.userDao = userDao;
        this.invoiceDao = invoiceDao;
        this.googleApiService = googleApiService;
    }

    public DriveFileOperationServiceServiceImpl() {

    }

    @Override
    public void saveFile(Workbook workbook, Configuration configuration, Order order) {
        User user = userDao.findById((long) order.getCustomerId());
        LocalDateTime localDateTime = LocalDateTime.now();
        Path filePath = Path.of(user.getEmail() + "Invoice" + localDateTime.toLocalDate() + "xlsx");
        FileManager fileManager = new FileManager(filePath);
        File file;
        try {
            fileManager.writeExcelFileLocal(workbook);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            file = fileManager.getExcelFile(user.getEmail(), "Invoice" + localDateTime.toLocalDate() + "xlsx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileId = googleApiService.uploadFileIntoDrive("Invoice" + localDateTime.toLocalDate()+ ".xlsx", file);

        Invoice invoice = new Invoice();
        invoice.setOrderId((int) order.getOrderId());
        invoice.setFileId(fileId);
        invoice.setFileName(user.getEmail() + "\\Invoice" + localDateTime.toLocalDate() + ".xlsx");
        invoiceDao.create(invoice);
        System.out.println("file send");
    }

    @Override
    public File getFile(Configuration configuration, long customerId, long orderId) {
        Invoice invoice = invoiceDao.findByOrderId(orderId);
        User user = userDao.findById(customerId);
        FileManager fileManager = new FileManager();
        File file;
        try {
            fileManager.saveFileFromDrive(googleApiService.downloadFile(invoice.getFileId()), user.getEmail(),invoice.getFileName());
            file = fileManager.getExcelFile(user.getEmail(), invoice.getFileName());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        System.out.println("file get from drive");
        return file;
    }
}
