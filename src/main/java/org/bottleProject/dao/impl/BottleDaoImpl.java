package org.bottleProject.dao.impl;

import org.bottleProject.dao.BottleDao;
import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Repository
public class BottleDaoImpl extends AbstractDaoImpl<Bottle> implements BottleDao {

    public BottleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Bottle> getAll() {
        return getJdbcTemplate().query("SELECT * FROM bottle;", BeanPropertyRowMapper.newInstance(Bottle.class));
    }

    @Override
    public Long create(Bottle entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO bottle (name_bottle, volume_id, soda, plastic, create_date , reserved, producer, storage_id, price_id) VALUES(?,?,?,?,?,?,?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,entity.getNameBottle());
            stmt.setInt(2,entity.getVolumeId());
            stmt.setBoolean(3,entity.isSoda());
            stmt.setBoolean(4,entity.isPlastic());
            stmt.setTimestamp(5, Timestamp.valueOf(entity.getCreateDate()));
            stmt.setBoolean(6,entity.isReserved());
            stmt.setString(7,entity.getProducer());
            stmt.setInt(8,entity.getStorageId());
            stmt.setInt(9,entity.getPriceId());
            return stmt;
        }, keyHolder);
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue()).getBottleId();
    }

    @Override
    public Bottle findById(Long id) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM bottle WHERE bottle_id=?",
                    BeanPropertyRowMapper.newInstance(Bottle.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    @Override
    public Bottle update(Bottle entity, Long id) {
        getJdbcTemplate().update("UPDATE bottle SET price_id=? WHERE bottle_id=?",
                entity.getPriceId(), entity.getBottleId() );
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM bottle WHERE bottle_id=?", id);
    }

    @Override
    public List<Bottle> filterBy(BottleFilterDto bottleFilterDto) {
        return getJdbcTemplate().query("SELECT * FROM bottle ORDER BY "+bottleFilterDto.getSortBy().toString().toLowerCase()+" LIMIT ? OFFSET ? ;",
                BeanPropertyRowMapper.newInstance(Bottle.class),
                bottleFilterDto.getVolumeId(), bottleFilterDto.getOffset());
    }
}
