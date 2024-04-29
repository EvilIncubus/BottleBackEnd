package org.bottleProject.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.entity.Order;
import org.bottleProject.service.InvoiceFileOperationService;
import org.bottleProject.service.InvoicingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoicingService {

    private final OrderDao orderDao;

    private final InvoiceFileOperationService invoiceFileOperationService;

    public InvoiceServiceImpl(OrderDao orderDao, InvoiceFileOperationService invoiceFileOperationsService) {
        this.orderDao = orderDao;
        this.invoiceFileOperationService = invoiceFileOperationsService;
    }

    @Override
    public void invoicing(InvoiceWrapper invoiceWrapper) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoice");
        createHeader(workbook, sheet);
        createCustomerAndCompanyRows(workbook, sheet, invoiceWrapper);

        double totalSum = 0;
        int column = 7;
        for (BottleListWrapper bottleListWrapper : invoiceWrapper.getBottleListDtoList()) {
            createBottleOrderRows(workbook, sheet, bottleListWrapper, column);
            column++;
            totalSum += bottleListWrapper.getPrice() * bottleListWrapper.getAmountBottle();
        }

        calcTotalSum(totalSum, column, workbook, sheet);
        for (int i = 0; i < 11; i++) {
            sheet.autoSizeColumn(i);
        }
        Order order = orderDao.findById(invoiceWrapper.getOrderId());
        invoiceFileOperationService.fileToSave(workbook,order);
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
        valuesToWrite.add("Bottle Volume");
        valuesToWrite.add("Sugar");
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
        valuesToWrite.add(String.valueOf(orderDto.getOrderId()));
        valuesToWrite.add(orderDto.getLastName());
        valuesToWrite.add(orderDto.getFirstName());
        setAddValues(sheet, valuesToWrite, headerCellStyle, 2, 1);

        valuesToWrite = new ArrayList<>();
        valuesToWrite.add(String.valueOf(orderDto.getCreatedDate()));
        valuesToWrite.add(orderDto.getCompany());
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
        valuesToWrite.add(String.valueOf(finalOrderDto.getBottleId()));
        valuesToWrite.add(finalOrderDto.getNameBottle());
        valuesToWrite.add(String.valueOf(finalOrderDto.getBottleVolume()));
        if (finalOrderDto.isSugar()) {
            valuesToWrite.add("Yes");
        } else {
            valuesToWrite.add("No");
        }
        valuesToWrite.add(finalOrderDto.getPackaging());
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
