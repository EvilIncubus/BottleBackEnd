package org.bottleProject.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.nio.file.Path;

public class FileManager {

    private String folderName;
    private String fileName;
    private String fileExtension;
    private Path filePath;

    public FileManager(String folderName, String fileName, String fileExtension) {
        this.folderName = folderName;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    public FileManager() {

    }

    public FileManager(Path filePath) {
        this.filePath = filePath;
    }

    public void writeExcelFileLocal(Workbook xssfWorkbook) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath.toString());
        xssfWorkbook.write(outputStream);
    }

    public void writeExcelFile(Workbook xssfWorkbook) {
        StringBuilder filePath = new StringBuilder();
        filePath.append("C:\\Users\\VODAI\\Documents");
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
        String path = "C:\\Users\\VODAI\\Documents\\" + userEmail + "\\" + fileName;
        return new File(path);
    }

    public void saveFileFromDrive(InputStream inputStream, String email, String fileName){
        try {
            Workbook book = WorkbookFactory.create(inputStream);
            String path = "C:\\Users\\VODAI\\Documents\\" + email + "\\" + fileName;
            FileOutputStream outputStream = new FileOutputStream(path);
            book.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

