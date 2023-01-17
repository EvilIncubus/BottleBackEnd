package org.bottleProject.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private final Properties properties;

    private static final Logger logger = LogManager.getLogger(PropertiesReader.class);

    private String fileName;
    public PropertiesReader(String fileName) {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(this.fileName = fileName));
        } catch (IOException exception) {
            exception.getStackTrace();
        }
    }

    public String readProperty(String keyName) {
        return properties.getProperty(keyName, "Key name not found");
    }

    public Properties loadProperties() {

        ClassLoader loader = this.getClass().getClassLoader();
        try (InputStream stream = loader.getResourceAsStream(fileName)) {
            properties.load(stream);
            logger.info("loaded resources as stream");
        } catch (IOException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return properties;
    }
}
