package org.bottleProject.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bottleProject.dao.CustomerDao;
import org.bottleProject.dao.InvoiceDao;
import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.entity.Customer;
import org.bottleProject.entity.Invoice;
import org.bottleProject.entity.Order;
import org.bottleProject.service.GoogleApiService;
import org.bottleProject.service.InvoicingService;
import org.bottleProject.util.FileManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoicingService {

    private final OrderDao orderDao;
    private final InvoiceDao invoiceDao;
    private final CustomerDao customerDao;
    private final GoogleApiService googleApiService;

    public InvoiceServiceImpl(OrderDao orderDao, InvoiceDao invoiceDao, CustomerDao customerDao, GoogleApiService googleApiService) {
        this.orderDao = orderDao;
        this.invoiceDao = invoiceDao;
        this.customerDao = customerDao;
        this.googleApiService = googleApiService;
    }

    Invoice invoice = new Invoice();

    @Override
    public Workbook prepareInvoice(Order order) {
        InvoiceWrapper invoiceWrapper = orderDao.getOrderInvoice(order);
        LocalDateTime localDateTime = LocalDateTime.now();
        return invoicing(invoiceWrapper, localDateTime);
//        FileManager fileManager = new FileManager();
//        File file;
//        try {
//            file = fileManager.getExcelFile(invoiceWrapper.getEmail(), "Invoice" + localDateTime.toLocalDate() + ".xlsx");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        String fileId = googleApiService.uploadFileIntoDrive("Invoice" + localDateTime.toLocalDate()+ ".xlsx", file);
//
//        invoice.setOrderId((int) order.getOrderId());
//        invoice.setFileId(fileId);
//        invoice.setFileName(invoiceWrapper.getEmail() + "\\Invoice" + localDateTime.toLocalDate() + ".xlsx");
//        invoiceDao.create(invoice);
    }
    @Override
    public File getInvoiceContents(long customerId, long orderId) {
        invoice = invoiceDao.findByOrderId(orderId);
        Customer customer = customerDao.findById(customerId);
        FileManager fileManager = new FileManager();
        File file;
        try {
            file = fileManager.getExcelFile(customer.getEmail(), invoice.getFileName());
        } catch (IOException e) {
            try {
                fileManager.saveFileFromDrive(googleApiService.downloadFile(invoice.getFileId()), customer.getEmail(),invoice.getFileName());
                file = fileManager.getExcelFile(customer.getEmail(), invoice.getFileName());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
        return file;
    }

    @Override
    public Workbook invoicing(InvoiceWrapper invoiceWrapper, LocalDateTime localDateTime) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoice");
        createHeader(workbook, sheet);
        createCustomerAndCompanyRows(workbook, sheet, invoiceWrapper);

        double totalSum = 0;
        int column = 7;
        for (BottleListWrapper bottleListWrapper : invoiceWrapper.getBottleListDtoList()) {
            createBottleOrderRows(workbook, sheet, bottleListWrapper, column);
            column++;
            totalSum += bottleListWrapper.getPrice();
        }

        calcTotalSum(totalSum, column, workbook, sheet);
        for (int i = 0; i < 11; i++) {
            sheet.autoSizeColumn(i);
        }
        FileManager fileManager = new FileManager(invoiceWrapper.getEmail(), "Invoice" + localDateTime.toLocalDate(), "xlsx");
        fileManager.writeExcelFile(workbook);
        return workbook;
    }

    private void createHeader(Workbook workbook, Sheet sheet) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        List<String> valuesToWrite = new ArrayList<>();
        valuesToWrite.add("OrderID");
        valuesToWrite.add("Customer");
        valuesToWrite.add("Address Delivery");
        setValues(sheet, valuesToWrite, headerCellStyle, 1, 1);

        valuesToWrite = new ArrayList<>();
        valuesToWrite.add("Order Date");
        valuesToWrite.add("Product Company");
        setAddValues(sheet, valuesToWrite, headerCellStyle, 7, 1);

        valuesToWrite = new ArrayList<>();
        valuesToWrite.add("BottleID");
        valuesToWrite.add("Bottle Name");
        valuesToWrite.add("Size");
        valuesToWrite.add("Soda");
        valuesToWrite.add("Plastic/Glass Bottle");
        valuesToWrite.add("Amount Bottle");
        valuesToWrite.add("Price per Bottle");
        valuesToWrite.add("Total");
        setValuesPlus(sheet, valuesToWrite, headerCellStyle, 1, 6);

    }

    private void setValuesPlus(Sheet sheet, List<String> valuesToWrite, CellStyle headerCellStyle, int cellNum, int rowNum) {
        Row row = sheet.createRow(rowNum);
        for (String value : valuesToWrite) {
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue(value);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void setValues(Sheet sheet, List<String> valuesToWrite, CellStyle headerCellStyle, int cellNum, int rowNum) {
        for (String value : valuesToWrite) {
            Row row = sheet.createRow(rowNum++);
            Cell cell = row.createCell(cellNum);
            cell.setCellValue(value);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void setAddValues(Sheet sheet, List<String> valuesToWrite, CellStyle headerCellStyle, int cellNum, int rowNum) {
        for (String value : valuesToWrite) {
            Row row = sheet.getRow(rowNum++);
            Cell cell = row.createCell(cellNum);
            cell.setCellValue(value);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void createCustomerAndCompanyRows(Workbook workbook, Sheet sheet, InvoiceWrapper orderDto) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        List<String> valuesToWrite = new ArrayList<>();
        valuesToWrite.add(String.valueOf(orderDto.getOrderID()));
        valuesToWrite.add(orderDto.getEmail());
        valuesToWrite.add(orderDto.getDeliveryAddress());
        setAddValues(sheet, valuesToWrite, headerCellStyle, 2, 1);

        valuesToWrite = new ArrayList<>();
        valuesToWrite.add(String.valueOf(orderDto.getCurentDate()));
        valuesToWrite.add(orderDto.getProducer());
        setAddValues(sheet, valuesToWrite, headerCellStyle, 8, 1);
    }

    private void createBottleOrderRows(Workbook workbook, Sheet sheet, BottleListWrapper finalOrderDto, int i) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        List<String> valuesToWrite = new ArrayList<>();
        valuesToWrite.add(String.valueOf(finalOrderDto.getBottleID()));
        valuesToWrite.add(finalOrderDto.getNameBottle());
        valuesToWrite.add(String.valueOf(finalOrderDto.getSize()));
        if (finalOrderDto.isCO2()) {
            valuesToWrite.add("Yes");
        } else {
            valuesToWrite.add("No");
        }
        if (finalOrderDto.isPlastic()) {
            valuesToWrite.add("Plastic");
        } else {
            valuesToWrite.add("Glass");
        }
        valuesToWrite.add(String.valueOf(finalOrderDto.getAmountBottle()));
        valuesToWrite.add(String.valueOf(finalOrderDto.getPrice()));
        setValuesPlus(sheet, valuesToWrite, headerCellStyle, 1, i);

        Cell cell7 = sheet.getRow(i).createCell(8);
        cell7.setCellValue(finalOrderDto.getAmountBottle() * finalOrderDto.getPrice());
        cell7.setCellStyle(headerCellStyle);

    }

    private void calcTotalSum(double totalSum, int count, Workbook workbook, Sheet sheet) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setTopBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setRightBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

        Cell cell = sheet.createRow(count + 1).createCell(8);
        cell.setCellValue(totalSum);
        cell.setCellStyle(headerCellStyle);
    }
}

//    public void writeSheetData(Object[][] data, Sheet sheet) {
//        CellStyle headerStyle = getCellStyle(sheet, true, true, (short) 300, HorizontalAlignment.CENTER);
//        CellStyle dataStyle = getCellStyle(sheet, false, false, (short) 240, HorizontalAlignment.CENTER);
//
//        int rows = data.length;
//        int columns = data[0].length;
//        for (int i = 0; i < rows; i++) {
//            Row row = sheet.createRow(i);
//
//            for (int j = 0; j < columns; j++) {
//                Cell cell = row.createCell(j);
//
//                if (i == 0) {
//                    cell.setCellStyle(headerStyle);
//                } else {
//                    cell.setCellStyle(dataStyle);
//                }
//
//                Object value = data[i][j];
//
//                if (value instanceof String) {
//                    cell.setCellValue((String) value);
//                } else if (value instanceof Long) {
//                    cell.setCellValue((Long) value);
//                } else if (value instanceof Float) {
//                    cell.setCellValue((Float) value);
//                } else if (value instanceof Integer) {
//                    cell.setCellValue((Integer) value);
//                } else if (value instanceof LocalDate) {
//                    cell.setCellValue((LocalDate) value);
//                }
//            }
//        }
//    }
