package org.example.connectionDB;

import org.example.util.PropertiesReader;

import java.sql.*;
;

public class ConnectionDB implements JdbcConnection {

    private static final String PROPERTY_URL_FIELD = "url";
    private static final String PROPERTY_USERNAME_FIELD = "user";
    private static final String PROPERTY_PASSWORD_FIELD = "password";

    private final static PropertiesReader propertiesReader = new PropertiesReader("application.properties");
    private static ConnectionDB connectionDB= null;

    private ConnectionDB() {

    }

    public static ConnectionDB getInstance() {
        if (connectionDB == null) {
            connectionDB = new ConnectionDB();
        }

        return connectionDB;
    }

    public Connection getConnection() {

        String url = propertiesReader.readProperty(PROPERTY_URL_FIELD);
        String user = propertiesReader.readProperty(PROPERTY_USERNAME_FIELD);
        String password = propertiesReader.readProperty(PROPERTY_PASSWORD_FIELD);

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
