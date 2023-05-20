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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BottleDaoImpl extends AbstractDaoImpl<Bottle> implements BottleDao {

    public BottleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Bottle> getAll(int size, int offset) {
        return getJdbcTemplate().query("SELECT * FROM bottle limit ? offset ?;", BeanPropertyRowMapper.newInstance(Bottle.class), size, offset);
    }

    @Override
    public Bottle create(Bottle entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO bottle (name_bottle, volume_id, sugar, create_date, stock, producer, storage_id, bottle_category_id, bottle_packaging_id, bottle_photo) VALUES(?,?,?,?,?,?,?,?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getNameBottle());
            stmt.setInt(2, entity.getVolumeId());
            stmt.setBoolean(3, entity.isSugar());
            stmt.setTimestamp(4, Timestamp.valueOf(entity.getCreateDate()));
            stmt.setInt(5, entity.getStock());
            stmt.setString(6, entity.getProducer());
            stmt.setInt(7, entity.getStorageId());
            stmt.setInt(8, entity.getBottleCategoryId());
            stmt.setInt(9, entity.getBottlePackagingId());
            stmt.setString(10, entity.getBottlePhoto());
            return stmt;
        }, keyHolder);
        return findById(Objects.requireNonNull(keyHolder.getKey()).longValue());
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
        getJdbcTemplate().update("UPDATE bottle SET name_bottle= ? WHERE bottle_id= ? ",
                entity.getNameBottle(), entity.getBottleId());
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM bottle WHERE bottle_id=?", id);
    }


    @Override
    public List<Bottle> filterBy(BottleFilterDto bottleFilterDto) {

        StringBuilder query = new StringBuilder("SELECT * FROM bottle " +
                " inner join bottle_category on bottle_category.bottle_category_id = bottle.bottle_category_id " +
                " inner join bottle_packaging on bottle_packaging.bottle_packaging_id = bottle.bottle_packaging_id " +
                " inner join price on price.bottle_id = bottle.bottle_id " +
                " inner join volume on volume.volume_id = bottle.volume_id " +
                " Where 1 = 1 ");
        if (!bottleFilterDto.getListOfCategories().isEmpty()) {
            query.append(" And bottle_category.category IN (");
            for (String category : bottleFilterDto.getListOfCategories()) {
                if (!category.isEmpty()) {
                    query.append(" '").append(category).append("',");
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        }

        if (!bottleFilterDto.getPackaging().isEmpty()) {
            query.append(" And bottle_packaging.packaging IN (");
            for (String packaging : bottleFilterDto.getPackaging()) {
                if (!packaging.isEmpty()) {
                    query.append(" '").append(packaging).append("',");
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        }
        if (!bottleFilterDto.getVolume().isEmpty()) {
            query.append(" And volume.volume IN (");
            for (Double volume : bottleFilterDto.getVolume()) {
                if (volume != 0) {
                    query.append(" ").append(volume).append(",");
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        }
        if (bottleFilterDto.getMinPrice() != 0) {
            query.append(" And price.price > ").append(bottleFilterDto.getMinPrice()).append(" ");
        }
        if (bottleFilterDto.getMaxPrice() != 0) {
            query.append(" And price.price < ").append(bottleFilterDto.getMaxPrice()).append(" ");
        }
        if (bottleFilterDto.getSugar() != null) {
            query.append(" And bottle.sugar = ").append(bottleFilterDto.getSugar()).append(" ");
        }
        query.append(" Limit ").append(bottleFilterDto.getSize()).append(" Offset ").append(bottleFilterDto.getOffset());
        return getJdbcTemplate().query(
                query.toString(),
                BeanPropertyRowMapper.newInstance(Bottle.class));
    }

    @Override
    public Integer countAllFilterBottle(BottleFilterDto bottleFilterDto) {
        StringBuilder query = new StringBuilder("SELECT count(*) FROM bottle " +
                " inner join bottle_category on bottle_category.bottle_category_id = bottle.bottle_category_id " +
                " inner join bottle_packaging on bottle_packaging.bottle_packaging_id = bottle.bottle_packaging_id " +
                " inner join price on price.bottle_id = bottle.bottle_id " +
                " inner join volume on volume.volume_id = bottle.volume_id" +
                " Where 1 = 1 ");
        if (!bottleFilterDto.getListOfCategories().isEmpty()) {
            query.append(" And bottle_category.category IN (");
            for (String category : bottleFilterDto.getListOfCategories()) {
                if (!category.isEmpty()) {
                    query.append(" '").append(category).append("',");
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        }

        if (!bottleFilterDto.getPackaging().isEmpty()) {
            query.append(" And bottle_packaging.packaging IN (");
            for (String packaging : bottleFilterDto.getPackaging()) {
                if (!packaging.isEmpty()) {
                    query.append(" '").append(packaging).append("',");
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        }
        if (!bottleFilterDto.getVolume().isEmpty()) {
            query.append(" And volume.volume IN (");
            for (Double volume : bottleFilterDto.getVolume()) {
                if (volume != 0) {
                    query.append(" ").append(volume).append(",");
                }
            }
            query.deleteCharAt(query.length() - 1);
            query.append(")");
        }
        if (bottleFilterDto.getMinPrice() != 0) {
            query.append(" And price.price > ").append(bottleFilterDto.getMinPrice()).append(" ");
        }
        if (bottleFilterDto.getMaxPrice() != 0) {
            query.append(" And price.price < ").append(bottleFilterDto.getMaxPrice()).append(" ");
        }
        if (bottleFilterDto.getSugar() != null) {
            query.append(" And bottle.sugar = ").append(bottleFilterDto.getSugar()).append(" ");
        }
        return getJdbcTemplate().queryForObject(query.toString(), Integer.class);
    }

    @Override
    public Integer countAllBottle() {
        return getJdbcTemplate().queryForObject("select count(*) from bottle", Integer.class);
    }

    @Override
    public List<Bottle> searchBottle(String search, int size, int page) {
        return getJdbcTemplate().query("SELECT * FROM bottle where name_bottle like '%"+ search +"%' limit "+size+" offset "+page+";", BeanPropertyRowMapper.newInstance(Bottle.class));
    }

    @Override
    public Integer countSearchBottle(String search) {
        return getJdbcTemplate().queryForObject("select count(*) from bottle where name_bottle like '%"+ search +"%'", Integer.class);
    }
}
