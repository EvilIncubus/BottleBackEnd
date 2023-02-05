package org.bottleProject.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import java.io.InputStream;
import java.util.List;

public interface GoogleApiService {
    public String uploadFileIntoDrive(String fileName, java.io.File fileToInsert);

    public InputStream downloadFile(String realFileId) throws GoogleJsonResponseException;

    public List<com.google.api.services.drive.model.File> getAllFilesFromDrive();
}
