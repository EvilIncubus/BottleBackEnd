package org.bottleProject.util;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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

    public File getExcelFile(String userEmail, String file) throws IOException {
        String path = "C:\\Users\\VODAI\\Documents\\" + userEmail + "\\" + file;
        return new File(path);
    }

    public void saveFileFromDrive(InputStream inputStream){

    }
}

