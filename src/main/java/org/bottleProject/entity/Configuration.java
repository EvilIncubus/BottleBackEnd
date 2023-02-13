package org.bottleProject.entity;

import org.bottleProject.entity.enums.RequestType;

public class Configuration {
    String configurationName;
    String detailsParams;
    String configValue;

    public Configuration(String configurationName, String detailsParams, String configValue) {
        this.configurationName = configurationName;
        this.detailsParams = detailsParams;
        this.configValue = configValue;
    }

    public Configuration() {

    }



}
