package org.bottleProject.dao.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.FullOrderDto;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
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

@Repository
public class OrderDaoImpl extends AbstractDaoImpl<Order> implements OrderDao {

    public OrderDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Order> getAll(int size, int offset) {
        return getJdbcTemplate().query("SELECT * FROM orders;", BeanPropertyRowMapper.newInstance(Order.class));
    }

    @Override
    public Order create(Order entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO orders (profile_id, delivery_address, curent_date, status_id) VALUES(?,?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, entity.getProfileId());
            stmt.setInt(2, entity.getAddressId());
            stmt.setTimestamp(3, Timestamp.valueOf(entity.getCreatedDate()));
            stmt.setInt(4, entity.getStatusId());
            return stmt;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue());
    }

    @Override
    public Order findById(Long id) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM orders WHERE order_id=?",
                    BeanPropertyRowMapper.newInstance(Order.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Order update(Order entity, Long id) {
        getJdbcTemplate().update("UPDATE delivery_address as a join orders as o on o.delivery_address_id = a.delivery_address_id " +
                        "SET delivery_address = ? WHERE order_id=?",
                entity.getAddressId(), entity.getOrderId());
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM orders WHERE order_id=?", id);
    }

    @Override
    public List<Order> allCustomerOrder(long id) {
        return getJdbcTemplate().query("SELECT * FROM orders Where profile_id=?;", BeanPropertyRowMapper.newInstance(Order.class), id);
    }

    @Override
    public String setOrderBottles(OrderBottle orderBottle) {
        getJdbcTemplate().update("INSERT INTO order_bottle (bottle_id, order_id,amount_bottle) VALUES(?,?,?) ON DUPLICATE KEY UPDATE amount_bottle = ?;",
                orderBottle.getBottleId(), orderBottle.getOrderId(), orderBottle.getAmountBottle(), orderBottle.getAmountBottle());
        return "Success Create";
    }

    @Override
    public Long findOrderBottles(long orderID, long bottleID) {
        try {

            return getJdbcTemplate().queryForObject("SELECT order_bottle_id FROM order_bottle WHERE order_id=? and bottle_id =?",
                    Long.class, orderID, bottleID);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BottleListWrapper> getFinalOrder(Order order) {
        return getJdbcTemplate().query("select b.bottle_id, ob.amount_bottle, b.name_bottle, v.bottle_volume, p.price, b.soda, b.plastic, b.create_date \n" +
                "from orders as o \n" +
                "inner join order_bottle as ob on o.order_id = ob.order_id \n" +
                "inner join profile c on c.profile_id = o.profile_id \n" +
                "inner join bottle as b on b.bottle_id = ob.bottle_id \n" +
                "inner join price p on p.price_id = b.price_id \n" +
                "inner join volume v on v.volume_id = b.volume_id \n" +
                "where o.order_id = ? ;", BeanPropertyRowMapper.newInstance(BottleListWrapper.class), order.getOrderId());
    }

    @Override
    public InvoiceWrapper getOrderInvoice(Order order) {
        InvoiceWrapper orderDto;
        orderDto = getJdbcTemplate().queryForObject("select o.order_id, u.email, a.delivery_address, o.created_date, b.producer \n" +
                "from orders as o \n" +
                "inner join order_bottle as ob on o.order_id = ob.order_id \n" +
                "inner join profile c on c.profile_id = o.profile_id \n" +
                "inner join user u on c.user_id = u.user_id \n" +
                "Inner Join delivery_address as a on orders.delivery_address_id = a.delivery_address_id \n" +
                "inner join bottle as b on b.bottle_id = ob.bottle_id \n" +
                "where o.order_id = ? limit 1;", BeanPropertyRowMapper.newInstance(InvoiceWrapper.class), order.getOrderId());

        assert orderDto != null;
        orderDto.setBottleListDtoList(getFinalOrder(order));
        return orderDto;
    }

    @Override
    public List<Order> searchOrder(OrderSearchDto orderSearchDto) {
        List<Object> argsList = new ArrayList<>();
        String queryString = "";
        boolean containsCondition = false;
        queryString += "Select * From orders inner join status on order.status_id = status.status_id";
        if (!orderSearchDto.getDeliveryAddress().isEmpty()){
            queryString += " Where delivery_address Like Concat(? ,'%')";
            argsList.add(orderSearchDto.getDeliveryAddress());
            containsCondition =true;
        }
        if (orderSearchDto.getStatus() != null){
            if(containsCondition){
                queryString += " And";
            } else {
                queryString += " Where";
                containsCondition = true;
            }

            queryString += " status_id = ?";
            argsList.add(orderSearchDto.getStatus());
        }

        if(orderSearchDto.getFromDate()!=null){

            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " created_date AFTER ?";
            argsList.add(orderSearchDto.getFromDate());
        }

        if(orderSearchDto.getToDate() != null){

            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
            }

            queryString += " created_date BEFORE ?";
            argsList.add(orderSearchDto.getToDate());
        }

        queryString += ";";
        return getJdbcTemplate().query(queryString, BeanPropertyRowMapper.newInstance(Order.class), argsList.toArray());
    }

    @Override
    public Integer countSearchCustomerOrder(OrderSearchDto orderSearchDto) {
        List<Object> argsList = new ArrayList<>();
        String queryString = "";
        boolean containsCondition = false;
        queryString += "Select count(*) From orders inner join status on order.status_id = status.status_id";
        if (!orderSearchDto.getDeliveryAddress().isEmpty()){
            queryString += " Where delivery_address Like ?%";
            containsCondition =true;
        }
        if (orderSearchDto.getStatus() != null){
            if(containsCondition){
                queryString += " And";
            } else {
                queryString += " Where";
                containsCondition = true;
            }

            queryString += " status_id = ?";
            argsList.add(orderSearchDto.getStatus());
        }

        if(orderSearchDto.getFromDate()!=null){

            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
                containsCondition = true;
            }

            queryString += " created_date AFTER ?";
            argsList.add(orderSearchDto.getFromDate());
        }

        if(orderSearchDto.getToDate() != null){

            if (containsCondition) {
                queryString += " AND";
            } else {
                queryString += " WHERE";
            }

            queryString += " created_date BEFORE ?";
            argsList.add(orderSearchDto.getToDate());
        }
        queryString += ";";
        return getJdbcTemplate().queryForObject(queryString,
                Integer.class,
                argsList.toArray()
        );
    }

    @Override
    public Integer countFilterCustomerOrders(int orderId) {
        return getJdbcTemplate().queryForObject("select count(*) from orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join delivery_address as a on orders.delivery_address_id = a.delivery_address_id \n" +
                        "Inner Join Status on orders.status_id = status.status_id\n" +
                        "WHERE orders.profile_id = ?",
                Integer.class,
                orderId
        );
    }

    @Override
    public List<Order> getAllFilterCustomerOrder(int profileId, int page, int size) {
        return getJdbcTemplate().query("SELECT orders.order_id, u.email, a.delivery_address , orders.created_date, status.status FROM orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join delivery_address as a on orders.delivery_address_id = a.delivery_address_id \n" +
                        "Inner Join Status on orders.status_id = status.status_id\n" +
                        "WHERE orders.profile_id = ? limit ? offset ?;",
                BeanPropertyRowMapper.newInstance(Order.class),
                profileId,
                size,
                page);
    }

    @Override
    public List<FullOrderDto> getAllFilterOrder(int page, int size) {
        return getJdbcTemplate().query("SELECT orders.order_id, u.email, a.delivery_address , orders.created_date, status.status FROM orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join delivery_address as a on orders.delivery_address_id = a.delivery_address_id \n" +
                        "Inner Join Status on orders.status_id = status.status_id limit ? OFFSET ?;",
                BeanPropertyRowMapper.newInstance(FullOrderDto.class),
                size,
                page);
    }

    @Override
    public Integer countFilterOrders() {
        return getJdbcTemplate().queryForObject("select count(*) from orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join delivery_address as a on orders.delivery_address_id = a.delivery_address_id \n" +
                        "Inner Join Status on orders.status_id = status.status_id",
                Integer.class);
    }

    @Override
    public Integer getOrderStatus(String status) {
        return getJdbcTemplate().queryForObject("select status_id from status \n" +
                        "Where status Like '%"+status+"%'",
                Integer.class);
    }

    @Override
    public Integer getOrderAddress(String address) {
        return getJdbcTemplate().queryForObject("select address_id from address \n" +
                        "Where address Like '%"+address+"%'",
                Integer.class);
    }
}
