package org.example.dao.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.connectionDB.JdbcConnection;
import org.example.dao.BottleDao;
import org.example.dto.BottleFilterDto;
import org.example.entity.Bottle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BottleDaoImpl extends AbstractDaoImpl<Bottle> implements BottleDao<Bottle> {

    private static final Logger logger = LogManager.getLogger(BottleDaoImpl.class);


    public BottleDaoImpl(JdbcConnection jdbcConnection) {
        super(jdbcConnection);
    }

    @Override
    public List<Bottle> getAll() {
        List<Bottle> bottles = new ArrayList<>();
        String sql = "SELECT * FROM bottle;";
        ResultSet rs = null;
        try {
            rs = getConnectionStatement().executeQuery(sql);
            while (rs.next()) {
                Bottle bottle = new Bottle();
                    bottle.setBottleID(rs.getLong("BottleID"));
                    bottle.setName(rs.getString("NameBottle"));
                    bottle.setCO2(rs.getBoolean("CO2"));
                    bottle.setPlastic(rs.getBoolean("Plastic"));
                    bottle.setStorageID(rs.getInt("StorageID"));
                    bottle.setProducer(rs.getString("Producer"));
                    bottle.setReserved(rs.getBoolean("Reserved"));
                    bottle.setLocalDateTime(rs.getTimestamp("CreateDate").toLocalDateTime());
                    bottle.setVolume(rs.getInt("VolumeID"));
                    bottle.setPriceID(rs.getInt("PriceID"));
                bottles.add(bottle);
            }
        } catch (SQLException e) {
            logger.info("Couldn't get all bottles from BD");
            throw new RuntimeException(e);
        }
        return bottles;
    }

    @Override
    public long create(Bottle entity) {
        String sql = "INSERT INTO bottle (NameBottle, VolumeID, CO2, Plastic, CreateDate , Reserved, Producer, StorageID, PriceID) VALUES(?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getVolume());
            ps.setBoolean(3, entity.isCO2());
            ps.setBoolean(4, entity.isPlastic());
            ps.setTimestamp(5, Timestamp.valueOf(entity.getLocalDateTime()));
            ps.setBoolean(6, entity.isReserved());
            ps.setString(7, entity.getProducer());
            ps.setInt(8, entity.getStorageID());
            ps.setInt(9, entity.getPriceID());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't create bottle in BD");
            throw new RuntimeException(e);
        }
        return id();
    }

    private long id(){
        String sql1 = "SELECT max(BottleID) FROM bottle;";
        long bottleID = 0L;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql1);
            while (rs.next()) {
                bottleID = rs.getLong("max(BottleID)");
            }
        } catch (SQLException e) {
            logger.info("Couldn't find bottle from BD");
            throw new RuntimeException(e);
        }
        return bottleID;
    }


    @Override
    public Bottle findById(Long id) {
        String sql = "SELECT * \n" +
                "FROM bottle\n" +
                "WHERE BottleID =" + id + ";";
        Bottle bottle;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            bottle = null;
            while (rs.next()) {
                bottle = new Bottle();
                bottle.setBottleID(rs.getLong("BottleID"));
                bottle.setName(rs.getString("NameBottle"));
                bottle.setCO2(rs.getBoolean("CO2"));
                bottle.setPlastic(rs.getBoolean("Plastic"));
                bottle.setStorageID(rs.getInt("StorageID"));
                bottle.setProducer(rs.getString("Producer"));
                bottle.setReserved(rs.getBoolean("Reserved"));
                bottle.setLocalDateTime(rs.getTimestamp("CreateDate").toLocalDateTime());
                bottle.setVolume(rs.getInt("VolumeID"));
                bottle.setPriceID(rs.getInt("PriceID"));
            }
        } catch (SQLException e) {
            logger.info("Couldn't find bottle by id in BD");
            throw new RuntimeException(e);
        }
        return bottle;
    }

    @Override
    public Bottle update(Bottle entity, Long id) {
        String sql = "UPDATE bottle SET PriceID = ? WHERE BottleID =" + id + ";";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setInt(1, entity.getPriceID());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't update bottle in BD");
            throw new RuntimeException(e);
        }
        return findById(id);
    }

    @Override
    public Long removeById(Long id) {
        String sql = "DELETE FROM bottle WHERE BottleID=" + id + ";";
        try {
            getConnectionStatement().executeUpdate(sql);
        } catch (SQLException e) {
            logger.info("Couldn't remove bottle from BD");
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Bottle> filterBy(BottleFilterDto bottleFilterDto) {
        List<Bottle> bottles = new ArrayList<>();
        String sql = "SELECT * FROM bottle ORDER BY " + bottleFilterDto.getSortBy().toString() + " LIMIT "
                + bottleFilterDto.getSize() + " OFFSET  " + bottleFilterDto.getOffset() + " ;";
        ResultSet rs = null;
        try {
            rs = getConnectionStatement().executeQuery(sql);
            while (rs.next()) {
                Bottle bottle = new Bottle();
                bottle.setBottleID(rs.getLong("BottleID"));
                bottle.setName(rs.getString("NameBottle"));
                bottle.setCO2(rs.getBoolean("CO2"));
                bottle.setPlastic(rs.getBoolean("Plastic"));
                bottle.setStorageID(rs.getInt("StorageID"));
                bottle.setProducer(rs.getString("Producer"));
                bottle.setReserved(rs.getBoolean("Reserved"));
                bottle.setLocalDateTime(rs.getTimestamp("CreateDate").toLocalDateTime());
                bottle.setVolume(rs.getInt("VolumeID"));
                bottle.setPriceID(rs.getInt("PriceID"));
                bottles.add(bottle);
            }
        } catch (SQLException e) {
            logger.info("Couldn't get filtered bottles from BD");
            throw new RuntimeException(e);
        }
        return bottles;
    }
}
