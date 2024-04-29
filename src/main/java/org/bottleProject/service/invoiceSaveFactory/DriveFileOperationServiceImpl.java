package org.bottleProject.service.invoiceSaveFactory;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dao.InvoiceDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.DriveConfiguration;
import org.bottleProject.entity.Invoice;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.User;
import org.bottleProject.service.GoogleApiService;
import org.bottleProject.service.OperationsWithFile;
import org.bottleProject.util.FileManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Service
public class DriveFileOperationServiceImpl implements OperationsWithFile {

    private final UserDao userDao;
    private final InvoiceDao invoiceDao;
    private final GoogleApiService googleApiService;

    public DriveFileOperationServiceImpl(UserDao userDao, InvoiceDao invoiceDao, GoogleApiService googleApiService) {
        this.userDao = userDao;
        this.invoiceDao = invoiceDao;
        this.googleApiService = googleApiService;
    }

    @Override
    public void saveFile(Workbook workbook, Order order) {
        User user = userDao.findById((long) order.getProfileId());
        LocalDateTime localDateTime = LocalDateTime.now();
        FileManager fileManager = new FileManager();
        File file;
        fileManager.writeExcelFile(workbook);
        try {
            file = fileManager.getExcelFile(user.getEmail()+ "\\Invoice" + localDateTime.toLocalDate() + ".xlsx");
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
    public File getFile(long orderId) {
        Invoice invoice = invoiceDao.findByOrderId(orderId);
        User user = userDao.findByOrderId(orderId);
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
