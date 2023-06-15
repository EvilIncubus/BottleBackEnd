package org.bottleProject.dao.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.*;
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
import java.time.LocalDateTime;
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
        getJdbcTemplate().update("UPDATE address as a join orders as o on o.delivery_address_id = a.address_id " +
                        "SET address = ? WHERE order_id=?",
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
        orderDto = getJdbcTemplate().queryForObject("select p.first_name, p.last_name, u.email, p.phone_number, p.profile_photo_path, p.company, o.order_id, a.address, o.created_date, s.status\n" +
                "                from orders as o\n" +
                "                inner join profile p on p.profile_id = o.profile_id \n" +
                "                inner join user u on u.user_id = p.user_id \n" +
                "                Inner Join address as a on o.delivery_address_id = a.address_id \n" +
                "                Inner Join status as s on o.status_id = s.status_id \n" +
                "                where o.order_id = ?;", BeanPropertyRowMapper.newInstance(InvoiceWrapper.class), order.getOrderId());

        assert orderDto != null;
        orderDto.setBottleListDtoList(getFinalOrder(order));
        return orderDto;
    }

    @Override
    public List<FullOrderDto> filterOrders(OrderFilterDto orderSearchDto) {
        StringBuilder queryString = new StringBuilder("Select * From orders as o Inner Join profile as p on o.profile_id = p.profile_id\n" +
                "Inner Join user as u on p.user_id = u.user_id \n" +
                "Inner Join status as s on o.status_id = s.status_id where 1=1 \n");
        if (!orderSearchDto.getStatus().isEmpty()) {
            queryString.append(" And s.status IN (");
            for (String status : orderSearchDto.getStatus()) {
                if (!status.isEmpty()) {
                    queryString.append(" '").append(status).append("',");
                }
            }
            queryString.deleteCharAt(queryString.length() - 1);
            queryString.append(")");
        }
        if (orderSearchDto.getFromDate() != null) {
            queryString.append(" And o.created_date > '").append(orderSearchDto.getFromDate()).append("' ");
        }
        if (orderSearchDto.getToDate() != null) {
            queryString.append(" And o.created_date < '").append(orderSearchDto.getToDate()).append("' ");
        }
        queryString.append(" Limit ").append(orderSearchDto.getSize()).append(" Offset ").append(orderSearchDto.getOffset());
        queryString.append(";");
        return getJdbcTemplate().query(queryString.toString(), BeanPropertyRowMapper.newInstance(FullOrderDto.class));
    }

    @Override
    public Integer countFilterCustomerOrder(OrderFilterDto orderSearchDto) {
        StringBuilder queryString = new StringBuilder("Select count(*) From orders as o Inner Join profile as p on o.profile_id = p.profile_id\n" +
                "Inner Join user as u on p.user_id = u.user_id \n" +
                "Inner Join status as s on o.status_id = s.status_id where 1=1 \n");
        if (!orderSearchDto.getStatus().isEmpty()) {
            queryString.append(" And s.status IN (");
            for (String status : orderSearchDto.getStatus()) {
                if (!status.isEmpty()) {
                    queryString.append(" '").append(status).append("',");
                }
            }

        }
        if (orderSearchDto.getFromDate() != null) {
            queryString.append(" And o.created_date > '").append(orderSearchDto.getFromDate()).append("' ");
        }
        if (orderSearchDto.getToDate() != null) {
            queryString.append(" And o.created_date < '").append(orderSearchDto.getToDate()).append("' ");
        }
        queryString.append(";");
        return getJdbcTemplate().queryForObject(queryString.toString(), Integer.class);
    }

    @Override
    public Integer countFilterCustomerOrders(int orderId) {
        return getJdbcTemplate().queryForObject("select count(*) from orders as o\n" +
                        "Inner Join profile as p on o.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join address as a on o.delivery_address_id = a.address_id \n" +
                        "Inner Join status as s on o.status_id = s.status_id\n" +
                        "WHERE o.profile_id = ?",
                Integer.class,
                orderId
        );
    }

    @Override
    public List<FullOrderDto> getAllFilterCustomerOrder(int profileId, String operatorEmail, int page, int size) {
        return getJdbcTemplate().query("SELECT o.order_id, a.address, u.email, o.created_date, s.status FROM orders as o\n" +
                        "Inner Join profile as p on o.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join address as a on o.delivery_address_id = a.address_id \n" +
                        "Inner Join status as s on o.status_id = s.status_id\n" +
                        "WHERE o.profile_id = ? and o.operator_email = ? limit ? offset ?;",
                BeanPropertyRowMapper.newInstance(FullOrderDto.class),
                profileId,
                operatorEmail,
                size,
                page);
    }

    @Override
    public List<FullOrderDto> getAllFilterOrder(int page, int size) {
        return getJdbcTemplate().query("SELECT orders.order_id, u.email, a.address , orders.created_date, status.status, p.company FROM orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join address as a on orders.delivery_address_id = a.address_id \n" +
                        "Inner Join status on orders.status_id = status.status_id limit ? OFFSET ?;",
                BeanPropertyRowMapper.newInstance(FullOrderDto.class),
                size,
                page);
    }

    @Override
    public Integer countFilterOrders() {
        return getJdbcTemplate().queryForObject("select count(*) from orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join address as a on orders.delivery_address_id = a.address_id \n" +
                        "Inner Join status on orders.status_id = status.status_id",
                Integer.class);
    }

    @Override
    public Integer getOrderStatus(String status) {
        return getJdbcTemplate().queryForObject("select status_id from status \n" +
                        "Where status Like '%" + status + "%'",
                Integer.class);
    }

    @Override
    public Integer getOrderAddress(String address) {
        return getJdbcTemplate().queryForObject("select address_id from address \n" +
                        "Where address Like '%" + address + "%'",
                Integer.class);
    }

    @Override
    public List<FullOrderDto> getAllOperatorOrders(String email, int offset, int size) {
        return getJdbcTemplate().query("SELECT orders.order_id, u.email, a.address , orders.created_date, status.status, p.company, p.profile_id FROM orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join address as a on orders.delivery_address_id = a.address_id \n" +
                        "Inner Join status on orders.status_id = status.status_id where orders.operator_email = ? Order by orders.created_date DESC limit ? OFFSET ?;",
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
                        "Inner Join address as a on orders.delivery_address_id = a.address_id \n" +
                        "Inner Join status on orders.status_id = status.status_id where orders.operator_email = ? ",
                Integer.class, email);
    }

    @Override
    public List<FullOrderDto> searchCustomer(String search) {
        return getJdbcTemplate().query("SELECT * FROM bottle where name_bottle like '%" + search + "%' limit " + 5 + " offset " + 0 + ";", BeanPropertyRowMapper.newInstance(FullOrderDto.class));
    }

    @Override
    public FullOrderDto getFullOrder(long orderId) {
        return getJdbcTemplate().queryForObject("SELECT orders.order_id, u.email, a.address , orders.created_date, status.status, p.company FROM orders \n" +
                        "Inner Join profile as p on orders.profile_id = p.profile_id \n" +
                        "Inner Join user as u on p.user_id = u.user_id \n" +
                        "Inner Join address as a on orders.delivery_address_id = a.address_id \n" +
                        "Inner Join status on orders.status_id = status.status_id \n" +
                        "where orders.order_id = ?;",
                BeanPropertyRowMapper.newInstance(FullOrderDto.class),
                orderId);
    }

    @Override
    public void removeBottlesFromStore(int amountBottle, int bottleId) {
        getJdbcTemplate().update("UPDATE bottle SET stock = stock - ? WHERE bottle_id=?",
                amountBottle, bottleId);
    }

    @Override
    public List<FullOrderDto> searchOrders(OrderSearchDto searchOrderDto) {
        return getJdbcTemplate().query("SELECT orders.order_id, u.email, a.address , orders.created_date, status.status, p.company, p.profile_id FROM orders \n" +
                "                        Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                "                        Inner Join user as u on p.user_id = u.user_id \n" +
                "                        Inner Join address as a on orders.delivery_address_id = a.address_id \n" +
                "                        Inner Join status on orders.status_id = status.status_id where orders.operator_email = '" + searchOrderDto.getOperatorEmail() + "' and p.company like '%" + searchOrderDto.getCompany() + "%' Order by orders.created_date DESC limit " + searchOrderDto.getSize() + " offset " + searchOrderDto.getOffset() + ";", BeanPropertyRowMapper.newInstance(FullOrderDto.class));
    }

    @Override
    public Integer countSearchCustomerOrder(OrderSearchDto searchOrderDto) {
        return getJdbcTemplate().queryForObject("select count(*) FROM orders \n" +
                "                        Inner Join profile as p on orders.profile_id = p.profile_id\n" +
                "                        Inner Join user as u on p.user_id = u.user_id \n" +
                "                        Inner Join address as a on orders.delivery_address_id = a.address_id \n" +
                "                        Inner Join status on orders.status_id = status.status_id where orders.operator_email = '" + searchOrderDto.getOperatorEmail() + "' and p.company like '%" + searchOrderDto.getCompany() + "%'", Integer.class);
    }

    @Override
    public List<FullOrderDto> filterAllOrders(OrderAllFilterDto filterOrderDto) {
        return null;
    }

    @Override
    public Integer countFilterAllOrder(OrderAllFilterDto filterOrderDto) {
        return null;
    }

    @Override
    public List<FullOrderDto> searchAllOrders(OrderSearchAllDto searchOrderDto) {
        return null;
    }

    @Override
    public Integer countSearchAllOrder(OrderSearchAllDto searchOrderDto) {
        return null;
    }

    @Override
    public List<String> getDeliveryAddress() {
        return getJdbcTemplate().query("select a.address from address as a \n" +
                "inner join profile as p on a.profile_id = p.profile_id \n" +
                "inner join user as u on u.user_id = p.user_id \n" +
                "inner join user_role ur on ur.user_id = u.user_id \n" +
                "inner join role as r on r.role_id = ur.role_id where r.role_name = 'CUSTOMER' limit " + 5 + ";", BeanPropertyRowMapper.newInstance(String.class));
    }

    @Override
    public List<String> getSearchDeliveryAddress(String search) {
        return getJdbcTemplate().query("select a.address from address as a \n" +
                "inner join profile as p on a.profile_id = p.profile_id \n" +
                "inner join user as u on u.user_id = p.user_id \n" +
                "inner join user_role ur on ur.user_id = u.user_id \n" +
                "inner join role as r on r.role_id = ur.role_id where r.role_name = 'CUSTOMER' and p.company like '%" + search + "%' limit " + 5 + " offset " + 0 + ";", BeanPropertyRowMapper.newInstance(String.class));
    }

    @Override
    public List<Double> countYesterdayAmount(String operatorEmail, LocalDateTime start, LocalDateTime end) {
        return getJdbcTemplate().queryForList("select pr.price * ob.amount_bottle from orders as o \n" +
                "inner join order_bottle as ob on ob.order_id = o.order_id \n" +
                "inner join price as pr on pr.bottle_id = ob.bottle_id \n" +
                "inner join profile as p on o.profile_id = p.profile_id \n" +
                "inner join user as u on u.user_id = p.user_id \n" +
                "inner join user_role ur on ur.user_id = u.user_id \n" +
                "inner join role as r on r.role_id = ur.role_id where r.role_name = 'CUSTOMER' and o.operator_email = '" + operatorEmail + "' And o.created_date < '" + end + "' And o.created_date > '" + start + "';", Double.class);
    }

    @Override
    public List<Double> countAllYesterdayAmount(LocalDateTime start, LocalDateTime end) {
        return getJdbcTemplate().queryForList("select pr.price * ob.amount_bottle from orders as o \n" +
                "inner join order_bottle as ob on ob.order_id = o.order_id \n" +
                "inner join price as pr on pr.bottle_id = ob.bottle_id \n" +
                "inner join profile as p on o.profile_id = p.profile_id \n" +
                "inner join user as u on u.user_id = p.user_id \n" +
                "inner join user_role ur on ur.user_id = u.user_id \n" +
                "inner join role as r on r.role_id = ur.role_id where r.role_name = 'CUSTOMER' And o.created_date < '" + end + "' And o.created_date > '" + start + "';", Double.class);
    }

    @Override
    public String setBottleStatus(OrderBottle orderBottle) {
        getJdbcTemplate().update("UPDATE order_bottle SET bottle_status = ? WHERE order_bottle_id=?",
                orderBottle.isBottleStatus(), orderBottle.getOrderBottleId());
        return "Success Create";
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        getJdbcTemplate().update("UPDATE orders as o inner join status as s on s.status_id = o.status_id SET o.status_id = ? WHERE s.status=?",
                orderId, status);
    }
}
