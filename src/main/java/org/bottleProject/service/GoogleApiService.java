package org.bottleProject.service;

import java.util.List;

public interface GoogleApiService {
    public String uploadFileIntoDrive(String fileName, java.io.File fileToInsert);

    public List<com.google.api.services.drive.model.File> getAllFilesFromDrive();
}
