package org.bottleProject.service;

import org.bottleProject.entity.Configuration;

public interface DriveFileOperationService {
    String saveFileOnDrive(Configuration configuration);

    String readFileOnDrive(Configuration configuration);
}
