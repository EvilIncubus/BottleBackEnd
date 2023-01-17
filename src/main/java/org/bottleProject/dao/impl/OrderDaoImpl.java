package org.bottleProject.dao.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.entity.Order;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OrderDaoImpl extends AbstractDaoImpl<Order> implements OrderDao {

    public OrderDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Order> getAll() {
        return getJdbcTemplate().query("SELECT * FROM orders;", BeanPropertyRowMapper.newInstance(Order.class));
    }

    @Override
    public Long create(Order entity) {
        getJdbcTemplate().update("INSERT INTO orders (customer_id, delivery_address, curent_date, status_id) VALUES(?,?,?,?);",
                entity.getCustomerID(),
                entity.getDeliveryAddress(),
                entity.getLocalDateTime(),
                entity.getStatusID()
        );
        return findMaxId();
    }

    private Long findMaxId() {
        try {
            return getJdbcTemplate().queryForObject("SELECT max(order_id) FROM orders;",
                    Long.class);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
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
        getJdbcTemplate().update("UPDATE orders SET delivery_address = ? WHERE order_id=?",
                entity.getDeliveryAddress(), entity.getOrderID());
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM orders WHERE order_id=?", id);
    }

    @Override
    public List<Order> allCustomerOrder(long id){
        return getJdbcTemplate().query("SELECT * FROM orders Where customer_id=?;", BeanPropertyRowMapper.newInstance(Order.class), id);
    }


    @Override
    public String setOrderBottles(long order, long bottle) {
        int amountBottle = 1;
        getJdbcTemplate().update("INSERT INTO orderbottle (bottle_id, order_id,amount_bottle) VALUES(?,?,?);",
                bottle, order, amountBottle);
        return "Success Create";
    }

    @Override
    public String updateOrderBottles(long id) {
        Integer amount = 1;
        try {
            amount += getJdbcTemplate().queryForObject("SELECT AmountBottle FROM order_bottle WHERE order_bottle_id = ? ",
                    Integer.class, id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return "Order Bottle not found";
        }

        getJdbcTemplate().update("UPDATE order_bottle SET amount_bottle = ? WHERE order_bottle_id=?",
                amount, id);

        return "Success Update";
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
                "inner join customer c on c.customer_id = o.customer_id \n" +
                "inner join bottle as b on b.bottle_id = ob.bottle_id \n" +
                "inner join price p on p.price_id = b.price_id \n" +
                "inner join volume v on v.volume_id = b.volume_id \n" +
                "where o.order_id = ? ;", BeanPropertyRowMapper.newInstance(BottleListWrapper.class), order.getOrderID());
    }

    @Override
    public InvoiceWrapper getOrderInvoice(Order order) {
        InvoiceWrapper orderDto;
        orderDto = getJdbcTemplate().queryForObject("select o.order_id, c.name_company, o.delivery_address, o.curent_date, b.producer \n" +
                "from orders as o \n" +
                "inner join order_bottle as ob on o.order_id = ob.order_id \n" +
                "inner join customer c on c.customer_id = o.customer_id \n" +
                "inner join bottle as b on b.bottle_id = ob.bottle_id \n" +
                "where o.order_id = ? limit 1;", BeanPropertyRowMapper.newInstance(InvoiceWrapper.class), order.getOrderID());

        assert orderDto != null;
        orderDto.setBottleListDtoList(getFinalOrder(order));
        return orderDto;
    }
}
