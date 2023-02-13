package org.bottleProject.service;

import org.bottleProject.entity.Configuration;

public interface LocalFileOperationService {

    String saveFileLocal(Configuration configuration);

    String readFileLocal(Configuration configuration);
}
