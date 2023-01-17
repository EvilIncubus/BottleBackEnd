package org.bottleProject.dao.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
public class OrderDaoImpl extends AbstractDaoImpl<Order> implements OrderDao<Order> {
    private static final Logger logger = LogManager.getLogger(BottleDaoImpl.class);

    public OrderDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Order> getAll() {
        return getJdbcTemplate().query("SELECT * FROM orders;", BeanPropertyRowMapper.newInstance(Order.class));
    }

    @Override
    public Long create(Order entity) {
        getJdbcTemplate().update("INSERT INTO orders (CustomerID, DeliveryAddress, CurentDate, StatusID) VALUES(?,?,?,?);",
                entity.getCustomerID(),
                entity.getDeliveryAddress(),
                entity.getLocalDateTime(),
                entity.getStatusID()
        );
        return findMaxId();
    }

    private Long findMaxId() {
        try {
            return getJdbcTemplate().queryForObject("SELECT max(OrderID) FROM orders;",
                    Long.class);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Order findById(Long id) {
        try {

            return getJdbcTemplate().queryForObject("SELECT * FROM orders WHERE OrderID=?",
                    BeanPropertyRowMapper.newInstance(Order.class), id);
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public Order update(Order entity, Long id) {
        getJdbcTemplate().update("UPDATE orders SET DeliveryAddress = ? WHERE OrderID=?",
                entity.getDeliveryAddress(), entity.getOrderID());
        return findById(id);
    }

    @Override
    public void removeById(Long id) {
        getJdbcTemplate().update("DELETE FROM orders WHERE OrderID=?", id);
    }

    @Override
    public List<Order> allCustomerOrder(long id){
        return getJdbcTemplate().query("SELECT * FROM orders Where CustomerID=?;", BeanPropertyRowMapper.newInstance(Order.class), id);
    }


    @Override
    public String setOrderBottles(long order, long bottle) {
        int amountBottle = 1;
        getJdbcTemplate().update("INSERT INTO orderbottle (BottleID, OrderID, AmountBottle) VALUES(?,?,?);",
                bottle, order, amountBottle);
        return "Success Create";
    }

    @Override
    public String updateOrderBottles(long id) {
        Integer amount = 1;
        try {
            amount += getJdbcTemplate().queryForObject("SELECT AmountBottle FROM orderbottle WHERE OrderBottleID = ? ",
                    Integer.class, id);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return "Order Bottle not found";
        }

        getJdbcTemplate().update("UPDATE orderbottle SET AmountBottle = ? WHERE OrderBottleID=?",
                amount, id);

        return "Success Update";
    }

    @Override
    public Long findOrderBottles(long orderID, long bottleID) {
        try {

            return getJdbcTemplate().queryForObject("SELECT OrderBottleID FROM orderbottle WHERE OrderID=? and BottleID =?",
                    Long.class, orderID, bottleID);
        } catch (
                IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<BottleListWrapper> getFinalOrder(Order order) {
        return getJdbcTemplate().query("select b.BottleID, ob.AmountBottle, b.NameBottle, v.BottleVolume, p.Price, b.CO2, b.Plastic, b.CreateDate \n" +
                "from orders as o \n" +
                "inner join orderbottle as ob on o.OrderID = ob.OrderID \n" +
                "inner join customer c on c.CustomerID = o.CustomerID \n" +
                "inner join bottle as b on b.BottleID = ob.BottleID \n" +
                "inner join price p on p.PriceID = b.PriceID \n" +
                "inner join volume v on v.VolumeID = b.VolumeID \n" +
                "where o.OrderID = ? ;", BeanPropertyRowMapper.newInstance(BottleListWrapper.class), order.getOrderID());
    }

    @Override
    public InvoiceWrapper getOrderInvoice(Order order) {
        InvoiceWrapper orderDto;
        orderDto = getJdbcTemplate().queryForObject("select o.OrderID, c.NameCompany, o.DeliveryAddress, o.CurentDate, b.Producer \n" +
                "from orders as o \n" +
                "inner join orderbottle as ob on o.OrderID = ob.OrderID \n" +
                "inner join customer c on c.CustomerID = o.CustomerID \n" +
                "inner join bottle as b on b.BottleID = ob.BottleID \n" +
                "where o.OrderID = ? limit 1;", BeanPropertyRowMapper.newInstance(InvoiceWrapper.class), order.getOrderID());

        assert orderDto != null;
        orderDto.setBottleListDtoList(getFinalOrder(order));
        return orderDto;
    }
}
