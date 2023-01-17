package org.example.connectionDB;

import java.sql.Connection;

public interface JdbcConnection {

    Connection getConnection();
}
