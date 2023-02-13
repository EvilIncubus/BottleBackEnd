package org.bottleProject.service.impl;

import org.bottleProject.entity.Configuration;

public abstract class OperationsWithFile {
    Configuration configuration;
    abstract void saveFile();
    abstract void getFile();
}
