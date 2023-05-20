package org.bottleProject.dao.impl;

import org.bottleProject.dao.Dao;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public abstract class AbstractDaoImpl<T> implements Dao<T> {

    private final JdbcTemplate jdbcTemplate;

    protected AbstractDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }


}