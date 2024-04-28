package org.bottleProject.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.nio.file.Path;

public class FileManager {

    private String folderName;
    private String fileName;
    private String fileExtension;

    public FileManager(String folderName, String fileName, String fileExtension) {
        this.folderName = folderName;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    public FileManager() {

    }

    public void writeExcelFile(Workbook xssfWorkbook) {
        StringBuilder filePath = new StringBuilder();
        filePath.append("C:\\Users\\06001\\Documents");
        filePath.append("\\");
        filePath.append(folderName);

        try {
            boolean isCreated = new File(filePath.toString()).mkdir();

            filePath.append("\\");
            filePath.append(fileName);
            filePath.append(".");
            filePath.append(fileExtension);

            FileOutputStream outputStream = new FileOutputStream(filePath.toString());
            xssfWorkbook.write(outputStream);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public File getExcelFile(String userEmail, String fileName) throws IOException {
        String path = "C:\\Users\\06001\\Documents\\" + userEmail + "\\" + fileName;
        return new File(path);
    }

    public void saveFileFromDrive(InputStream inputStream, String email, String fileName){
        try {
            Workbook book = WorkbookFactory.create(inputStream);
            String path = "C:\\Users\\06001\\Documents\\" + email + "\\" + fileName;
            FileOutputStream outputStream = new FileOutputStream(path);
            book.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

