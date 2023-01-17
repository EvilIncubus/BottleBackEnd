package org.example.dao.impl;

import org.example.connectionDB.JdbcConnection;
import org.example.dao.Dao;

import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDaoImpl<T> implements Dao<T> {

    private final JdbcConnection jdbcConnection;

    public AbstractDaoImpl(JdbcConnection jdbcConnection) {
        this.jdbcConnection = jdbcConnection;
    }

    protected Statement getConnectionStatement() throws SQLException {
        try {
            return jdbcConnection.getConnection().createStatement();
        } catch (SQLException exception) {
            throw new SQLException();
        }
    }
}
