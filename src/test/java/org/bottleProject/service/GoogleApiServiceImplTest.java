package org.bottleProject.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("integration_test")
public class GoogleApiServiceImplTest {

    @Autowired
    private GoogleApiService googleApiService;

    @Test
    public void getAllFilesFromDriveTest(){
        List<com.google.api.services.drive.model.File> fileList = googleApiService.getAllFilesFromDrive();
        assertNotNull(fileList);
    }

    @Test
    public void uploadFileToDriveTest() {
        String id = googleApiService.uploadFileIntoDrive("Invoice2023-01-09.xlsx", new File("C:\\Users\\VODAI\\Documents\\sushiline@yaponamama.md\\Invoicing2023-01-09.xlsx"));
        assertNotNull(id);
    }

    @Test
    public void downloadFileFromDrive() throws GoogleJsonResponseException {
        InputStream inputStream = googleApiService.downloadFile("1iZi7r9Fl5-ApUXCjrea41of48QODyuSZ");
        assertNotNull(inputStream);
    }

}
