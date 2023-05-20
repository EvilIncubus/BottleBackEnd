package org.bottleProject.dao.impl;

import org.bottleProject.dao.InvoiceDao;
import org.bottleProject.entity.Invoice;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class InvoiceDaoImpl extends AbstractDaoImpl<Invoice> implements InvoiceDao {
    protected InvoiceDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Invoice> getAll(int size, int offset) {
        return getJdbcTemplate().query("SELECT * FROM invoice;", BeanPropertyRowMapper.newInstance(Invoice.class));
    }

    @Override
    public Invoice create(Invoice invoice) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO invoice (order_id, file_name, file_id) VALUES(?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,invoice.getOrderId());
            stmt.setString(2,invoice.getFileName());
            stmt.setString(3,invoice.getFileId());
            return stmt;
        }, keyHolder);
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public Invoice update(Invoice invoice, Long id) {
        getJdbcTemplate().update("UPDATE invoice SET file_name=? WHERE invoice_id=?",
                invoice.getFileName(), invoice.getInvoiceId() );
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM invoice WHERE invoice_id=?", id);
    }

    @Override
    public Invoice findById(Long id) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM invoice WHERE invoice_id=?",
                    BeanPropertyRowMapper.newInstance(Invoice.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Invoice findByOrderId(Long orderId) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM invoice WHERE order_id=?",
                    BeanPropertyRowMapper.newInstance(Invoice.class), orderId);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
