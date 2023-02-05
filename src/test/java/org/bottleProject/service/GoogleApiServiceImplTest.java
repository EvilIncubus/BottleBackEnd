package org.bottleProject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootTest
@ActiveProfiles("integration_test")
public class GoogleApiServiceImplTest {

    @Autowired
    private GoogleApiService googleApiService;

    @Test
    public void getAllFilesFromDrive() throws GeneralSecurityException, IOException {
        googleApiService.getAllFilesFromDrive().forEach(System.out::println);
    }

    @Test
    public void uploadFileToDrive() throws GeneralSecurityException, IOException {
        String id = googleApiService.uploadFileIntoDrive("Invoice2023-01-09.xlsx", new File("C:\\Users\\VODAI\\Documents\\sushiline@yaponamama.md\\Invoicing2023-01-09.xlsx"));
        System.out.println(id);
    }

}
