package org.example.dao.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.connectionDB.JdbcConnection;
import org.example.dao.UserDao;
import org.example.entity.Order;
import org.example.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao<User> {

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    public UserDaoImpl(JdbcConnection jdbcConnection) {
        super(jdbcConnection);
    }
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM userprofile;";
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getLong("UserID"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setCostumerID(rs.getInt("CustomerID"));
                user.setRoleID(rs.getInt("RoleID"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.info("Couldn't get users from BD");
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Long removeById(Long id) {
        String sql = "DELETE FROM userprofile WHERE UserID=" + id + ";";
        try {
            getConnectionStatement().executeUpdate(sql);
        } catch (SQLException e) {
            logger.info("Couldn't remove user by id in BD");
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * \n" +
                "FROM userprofile\n" +
                "WHERE UserID =" + id + ";";
        User user;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            user = null;
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getLong("UserID"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setCostumerID(rs.getInt("CustomerID"));
                user.setRoleID(rs.getInt("RoleID"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
            }
        } catch (SQLException e) {
            logger.info("Couldn't find user by id in BD");
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public long create(User entity) {
        String sql = "INSERT INTO userprofile (FirstName, LastName, Email, CustomerID, RoleID, PhoneNumber) VALUES(?,?,?,?,?,?);";
        String sql1 = "SELECT max(UserID) FROM userprofile;";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setString(3, entity.getEmail());
            ps.setInt(4, entity.getCostumerID());
            ps.setInt(5, entity.getRoleID());
            ps.setString(6, entity.getPhoneNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't create user in BD");
            throw new RuntimeException(e);
        }
        long userID = 0L;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql1);
            while (rs.next()) {
                userID = rs.getLong("max(UserID)");
            }
        } catch (SQLException e) {
            logger.info("Couldn't find customer from BD");
            throw new RuntimeException(e);
        }
        return userID;
    }

    @Override
    public User update(User entity, Long id) {
        String sql = "UPDATE userprofile SET PhoneNumber = ? WHERE UserID =" + id + ";";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setString(1, entity.getPhoneNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't update user in BD");
            throw new RuntimeException(e);
        }
        return findById(id);
    }

    @Override
    public User findByEmail(String userEmail) {
        String sql = "SELECT * \n" +
                "FROM userprofile\n" +
                "WHERE Email ='" + userEmail + "';";
        User user = null;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getLong("CustomerID"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setRoleID(rs.getInt("RoleID"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
            }
        } catch (SQLException e) {
            logger.info("Couldn't find user by email in BD");
            throw new RuntimeException(e);
        }
        return user;
    }
}
