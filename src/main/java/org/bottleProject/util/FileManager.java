package org.bottleProject.util;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;

public class FileManager {

    private String folderName;
    private String fileName;
    private String fileExtension;

    public FileManager(String folderName, String fileName, String fileExtension) {
        this.folderName = folderName;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
    }

    public void writeExcelFile(Workbook workbook) {
        StringBuilder filePath = new StringBuilder();
        filePath.append("C:\\Users\\VODAI\\Documents");
        filePath.append("\\");
        filePath.append(folderName);

        try{
            boolean isCreated = new File(filePath.toString()).mkdir();

            filePath.append("\\");
            filePath.append(fileName);
            filePath.append(".");
            filePath.append(fileExtension);

            FileOutputStream outputStream = new FileOutputStream(filePath.toString());
            workbook.write(outputStream);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }


}
