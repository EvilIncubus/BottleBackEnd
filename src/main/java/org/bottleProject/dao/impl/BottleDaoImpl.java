package org.bottleProject.dao.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bottleProject.dao.BottleDao;
import org.bottleProject.dto.BottleFilterDto;
import org.bottleProject.entity.Bottle;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class BottleDaoImpl extends AbstractDaoImpl<Bottle> implements BottleDao<Bottle> {

    private static final Logger logger = LogManager.getLogger(BottleDaoImpl.class);

    public BottleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Bottle> getAll() {
        return getJdbcTemplate().query("SELECT * FROM bottle;", BeanPropertyRowMapper.newInstance(Bottle.class));
    }

    @Override
    public Long create(Bottle entity) {
        getJdbcTemplate().update("INSERT INTO bottle (NameBottle, VolumeID, CO2, Plastic, CreateDate , Reserved, Producer, StorageID, PriceID) VALUES(?,?,?,?,?,?,?,?,?);",
                entity.getNameBottle(),
                entity.getVolume(),
                entity.isCO2(),
                entity.isPlastic(),
                Timestamp.valueOf(entity.getCreateDate()),
                entity.isReserved(),
                entity.getProducer(),
                entity.getStorageID(),
                entity.getPriceID());
        return findMaxId();
    }

    //todo please use camel case style only for java class name variable name and metods name, for sequel use under score _ like as a delimiter
    private Long findMaxId() {
        try {
            return getJdbcTemplate().queryForObject("SELECT max(BottleID) FROM bottle;",
                    Long.class);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    @Override
    public Bottle findById(Long id) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM bottle WHERE BottleID=?",
                    BeanPropertyRowMapper.newInstance(Bottle.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    @Override
    public Bottle update(Bottle entity, Long id) {
        getJdbcTemplate().update("UPDATE bottle SET PriceID=? WHERE BottleID=?",
                entity.getPriceID(), entity.getBottleID() );
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM bottle WHERE BottleID=?", id);
    }

    @Override
    public List<Bottle> filterBy(BottleFilterDto bottleFilterDto) {
        return getJdbcTemplate().query("SELECT * FROM bottle ORDER BY ? LIMIT ? OFFSET ? ;",
                BeanPropertyRowMapper.newInstance(Bottle.class), bottleFilterDto.getSortBy().toString(),
                bottleFilterDto.getSize(), bottleFilterDto.getOffset());
    }
}
