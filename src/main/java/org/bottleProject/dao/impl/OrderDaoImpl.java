package org.bottleProject.dao.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.FullOrderDto;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Bottle;
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

        String sql = "INSERT INTO orders (profile_id, delivery_address_id, created_date, status_id, operator_email) VALUES(?,?,?,?,?);";

        getJdbcTemplate().update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, entity.getProfileId());
            stmt.setInt(2, entity.getDeliveryAddressId());
            stmt.setTimestamp(3, Timestamp.valueOf(entity.getCreatedDate()));
            stmt.setInt(4, entity.getStatusId());
            stmt.setString(5, entity.getOperatorEmail());
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
                entity.getDeliveryAddressId(), entity.getOrderId());
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
        return getJdbcTemplate().query("select b.bottle_id, ob.amount_bottle, b.bottle_photo, b.name_bottle, v.bottle_volume, b.sugar, b.producer, p.price, b.stock, s.storage_section, bc.category, bp.packaging\n" +
                "                from order_bottle as ob \n" +
                "                inner join bottle as b on b.bottle_id = ob.bottle_id\n" +
                "                inner join volume as v on v.volume_id  = b.volume_id \n" +
                "                inner join price p on p.bottle_id  = b.bottle_id \n" +
                "                inner join storage s on s.storage_id = b.storage_id\n" +
                "                inner join bottle_category bc on bc.bottle_category_id = b.bottle_category_id\n" +
                "                inner join bottle_packaging bp on bp.bottle_packaging_id = b.bottle_packaging_id\n" +
                "                where ob.order_id = ? ;", BeanPropertyRowMapper.newInstance(BottleListWrapper.class), order.getOrderId());
    }

    @Override
    public InvoiceWrapper getOrderInvoice(Order order) {
        InvoiceWrapper orderDto;
        orderDto = getJdbcTemplate().queryForObject("select c.first_name, c.last_name, c.phone_number, c.profile_photo_path, c.company, o.order_id, a.delivery_address, o.created_date\n" +
                "                from orders as o\n" +
                "                inner join order_bottle as ob on o.order_id = ob.order_id \n" +
                "                inner join profile c on c.profile_id = o.profile_id \n" +
                "                Inner Join delivery_address as a on o.delivery_address_id = a.delivery_address_id \n" +
                "                inner join bottle as b on b.bottle_id = ob.bottle_id \n" +
                "                where o.order_id = ? limit 1;", BeanPropertyRowMapper.newInstance(InvoiceWrapper.class), order.getOrderId());

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
        return getJdbcTemplate().queryForObject("select count(*) from orders as o\n" +
                        "Inner Join profile as p on o.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join delivery_address as a on o.delivery_address_id = a.delivery_address_id \n" +
                        "Inner Join Status as s on o.status_id = s.status_id\n" +
                        "WHERE o.profile_id = ?",
                Integer.class,
                orderId
        );
    }

    @Override
    public List<FullOrderDto> getAllFilterCustomerOrder(int profileId, int page, int size) {
        return getJdbcTemplate().query("SELECT o.order_id, a.delivery_address, u.email, o.created_date, s.status FROM orders as o\n" +
                        "Inner Join profile as p on o.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join delivery_address as a on o.delivery_address_id = a.delivery_address_id \n" +
                        "Inner Join Status as s on o.status_id = s.status_id\n" +
                        "WHERE o.profile_id = ? limit ? offset ?;",
                BeanPropertyRowMapper.newInstance(FullOrderDto.class),
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

    @Override
    public List<FullOrderDto> getAllOperatorOrders(String email, int offset, int size) {
        return getJdbcTemplate().query("SELECT orders.order_id, u.email, a.delivery_address , orders.created_date, status.status FROM orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join delivery_address as a on orders.delivery_address_id = a.delivery_address_id \n" +
                        "Inner Join Status on orders.status_id = status.status_id where orders.operator_email = ? limit ? OFFSET ?;",
                BeanPropertyRowMapper.newInstance(FullOrderDto.class),
                email,
                size,
                offset);
    }

    @Override
    public Integer countOperatorOrders(String email) {
        return getJdbcTemplate().queryForObject("select count(*) from orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join delivery_address as a on orders.delivery_address_id = a.delivery_address_id \n" +
                        "Inner Join Status on orders.status_id = status.status_id where orders.operator_email = ? ",
                Integer.class, email);
    }

    @Override
    public List<FullOrderDto> searchCustomer(String search) {
        return getJdbcTemplate().query("SELECT * FROM bottle where name_bottle like '%"+ search +"%' limit "+5+" offset "+0+";", BeanPropertyRowMapper.newInstance(FullOrderDto.class));
    }
}
