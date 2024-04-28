package org.bottleProject.dao.impl;

import org.bottleProject.dao.NotificationDao;
import org.bottleProject.entity.Notification;
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
public class NotificationDaoImpl extends AbstractDaoImpl<Notification> implements NotificationDao {

    protected NotificationDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Notification> getAll(int size, int offset) {
        return null;
    }

    @Override
    public Notification create(Notification notification) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO notification (description, order_id, user_notification_list_json) VALUES(?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, notification.getDescription());
            stmt.setInt(2, notification.getOrderId());
            stmt.setString(3, notification.getUserNotificationListJson());
            return stmt;
        }, keyHolder);

        return findById((Objects.requireNonNull(keyHolder.getKey()).longValue()));
    }

    @Override
    public Notification update(Notification notification, Long id) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public Notification findById(Long id) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM notification WHERE notification_id=?",
                    BeanPropertyRowMapper.newInstance(Notification.class), id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Notification> getActiveNotificationForUser(String email, int size, int offset) {
        return getJdbcTemplate().query("SELECT * \n" +
                        " FROM notification, \n" +
                        "    JSON_TABLE( \n" +
                        "        user_notification_list_json, \n" +
                        "        '$.userList[*]' \n" +
                        "        COLUMNS ( \n" +
                        "            email VARCHAR(255) PATH '$.email', \n" +
                        "            readStatus BOOLEAN PATH '$.readStatus' \n" +
                        "        ) \n" +
                        "    ) AS users \n" +
                        " WHERE email = '?' AND readStatus = false limit ? offset ?;",
                BeanPropertyRowMapper.newInstance(Notification.class), email, size, offset);
    }

    @Override
    public Integer countActiveNotificationForUser(String email) {
        return getJdbcTemplate().queryForObject("SELECT count(*) \n" +
                        " FROM notification,\n" +
                        "    JSON_TABLE( \n" +
                        "        user_notification_list_json,\n" +
                        "        '$.userList[*]' \n" +
                        "        COLUMNS ( \n" +
                        "            email VARCHAR(255) PATH '$.email', \n" +
                        "            readStatus BOOLEAN PATH '$.readStatus' \n" +
                        "        ) \n" +
                        "    ) AS users \n" +
                        " WHERE email = ? AND readStatus = false;",
                Integer.class,
                email);
    }

    @Override
    public void updateUserNotificationReadStatus(String email, int orderId) {
        getJdbcTemplate().update("update notification set user_notification_list_json = JSON_Replace(user_notification_list_json, REPLACE(JSON_UNQUOTE(JSON_SEARCH(user_notification_list_json, 'one', '?', NULL, '$.userList[*].email')), '.email', '.readStatus'),true) \n" +
                        "where order_id = ? and JSON_SEARCH(user_notification_list_json , 'one', '?', NULL, '$.userList[*].email') IS NOT NULL;",
                email, orderId, email);
    }

    @Override
    public List<Notification> getAllNotificationForUser(String email, int size, int offset) {
        return getJdbcTemplate().query("SELECT * \n" +
                        " FROM notification, \n" +
                        "    JSON_TABLE( \n" +
                        "        user_notification_list_json, \n" +
                        "        '$.userList[*]' \n" +
                        "        COLUMNS ( \n" +
                        "            email VARCHAR(255) PATH '$.email', \n" +
                        "            readStatus BOOLEAN PATH '$.readStatus' \n" +
                        "        ) \n" +
                        "    ) AS users \n" +
                        " WHERE email = '?' limit ? offset ?;",
                BeanPropertyRowMapper.newInstance(Notification.class), email, size, offset);
    }

    @Override
    public Integer countAllNotificationForUser(String email) {
        return getJdbcTemplate().queryForObject("SELECT count(*) \n" +
                        " FROM notification,\n" +
                        "    JSON_TABLE( \n" +
                        "        user_notification_list_json,\n" +
                        "        '$.userList[*]' \n" +
                        "        COLUMNS ( \n" +
                        "            email VARCHAR(255) PATH '$.email', \n" +
                        "            readStatus BOOLEAN PATH '$.readStatus' \n" +
                        "        ) \n" +
                        "    ) AS users \n" +
                        " WHERE email = '?';",
                Integer.class,
                email);
    }

    @Override
    public List<Integer> getOrderIdByActiveNotification(int offset, int size) {
        return getJdbcTemplate().queryForList("SELECT DISTINCT order_id \n" +
                        " FROM notification, \n" +
                        "    JSON_TABLE( \n" +
                        "        user_notification_list_json, \n" +
                        "        '$.userList[*]' \n" +
                        "        COLUMNS ( \n" +
                        "            readStatus BOOLEAN PATH '$.readStatus' \n" +
                        "        ) \n" +
                        "    ) AS users \n" +
                        " WHERE readStatus = false limit ? offset ?;", Integer.class, size, offset);
    }
//    String updateQuery = "UPDATE notification " +
//            "SET user_notification_list_json = JSON_SET(user_notification_list_json, '$.userList[?].readStatus', true) " +
//            "WHERE JSON_EXTRACT(user_notification_list_json, '$.userList[*].email') = ? AND JSON_EXTRACT(user_notification_list_json, '$.userList[*].readStatus') = false";
//
//    getJdbcTemplate().update(updateQuery, "storeman");


}
