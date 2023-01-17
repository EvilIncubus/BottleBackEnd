package org.bottleProject.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private final Properties properties;

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
