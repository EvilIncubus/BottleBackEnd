package org.bottleProject.service;

import org.bottleProject.service.impl.GoogleApiServiceImpl;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class GoogleApiServiceImplTest {

    private GoogleApiService googleApiService;

    public GoogleApiServiceImplTest() {
        this.googleApiService = new GoogleApiServiceImpl("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Test
    public void getAllFilesFromDrive() throws GeneralSecurityException, IOException {
        googleApiService.getAllFilesFromDrive().forEach(System.out::println);
    }

    @Test
    public void uploadFileToDrive() throws GeneralSecurityException, IOException {
        String id = googleApiService.uploadFileIntoDrive("Invoice.xlsx", new File("C:\\Users\\VODAI\\Documents\\Yapona Mama\\Invoicing2023-01-09.xlsx"));
        System.out.println(id);
    }

}
