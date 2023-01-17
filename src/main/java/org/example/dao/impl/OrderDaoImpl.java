package org.example.dao.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.example.connectionDB.JdbcConnection;
import org.example.dao.OrderDao;
import org.example.dto.BottleListWrapper;
import org.example.dto.InvoiceWrapper;
import org.example.entity.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends AbstractDaoImpl<Order> implements OrderDao<Order> {
    private static final Logger logger = LogManager.getLogger(BottleDaoImpl.class);

    public OrderDaoImpl(JdbcConnection jdbcConnection) {
        super(jdbcConnection);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders;";
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getLong("OrderID"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setDeliveryAddress(rs.getString("DeliveryAddress"));
                order.setLocalDateTime(rs.getTimestamp("CurentDate").toLocalDateTime());
                orders.add(order);
            }
        } catch (SQLException e) {
            logger.info("Couldn't get order from BD");
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public long create(Order entity) {
        String sql = "INSERT INTO orders (CustomerID, DeliveryAddress, CurentDate, StatusID) VALUES(?,?,?,?);";
        String sql1 = "SELECT max(OrderID) FROM orders;";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setInt(1, entity.getCustomerID());
            ps.setString(2, entity.getDeliveryAddress());
            ps.setTimestamp(3, Timestamp.valueOf(entity.getLocalDateTime()));
            ps.setInt(4, entity.getStatusID());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't create order in BD");
            throw new RuntimeException(e);
        }
        long orderID = 0L;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql1);
            while (rs.next()) {
                orderID = rs.getLong("max(OrderID)");
            }
        } catch (SQLException e) {
            logger.info("Couldn't find order from BD");
            throw new RuntimeException(e);
        }
        return orderID;
    }

    @Override
    public Order findById(Long id) {
        String sql = "SELECT * \n" +
                "FROM orders\n" +
                "WHERE OrderID =" + id + ";";
        Order order;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            order = null;
            while (rs.next()) {
                order = new Order();
                order.setOrderID(rs.getLong("OrderID"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setDeliveryAddress(rs.getString("DeliveryAddress"));
                order.setLocalDateTime(rs.getTimestamp("CurentDate").toLocalDateTime());
            }
        } catch (SQLException e) {
            logger.info("Couldn't order find by id in BD");
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public Order update(Order entity, Long id) {
        String sql = "UPDATE orders SET DeliveryAddress = ? WHERE OrderID =" + id + ";";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setString(1, entity.getDeliveryAddress());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't update box in BD");
            throw new RuntimeException(e);
        }
        return findById(id);
    }

    @Override
    public Long removeById(Long id) {
        String sql = "DELETE FROM orders WHERE OrderID=" + id + ";";
        try {
            getConnectionStatement().executeUpdate(sql);
        } catch (SQLException e) {
            logger.info("Couldn't remove order by id in BD");
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public String setOrderBottles(long order, long bottle) {
        String sql = "INSERT INTO orderbottle (BottleID, OrderID, AmountBottle) VALUES(?,?,?);";
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setLong(1, bottle);
            ps.setLong(2, order);
            ps.setInt(3, 1);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't create orderBottle in BD");
            throw new RuntimeException(e);
        }
        return "Success Create";
    }

    @Override
    public String updateOrderBottles(long id) {
        String sql = "UPDATE orderbottle SET AmountBottle = ? WHERE OrderBottleID =" + id + ";";
        String sql1 = "SELECT AmountBottle FROM orderbottle WHERE OrderBottleID =" + id + ";";
        int amount = 1;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql1);
            while (rs.next()) {
                amount += rs.getLong("AmountBottle");
            }
        } catch (SQLException e) {
            logger.info("Couldn't create orderBottle in BD");
            throw new RuntimeException(e);
        }
        try {
            PreparedStatement ps
                    = getConnectionStatement().getConnection().prepareStatement(sql);
            ps.setInt(1, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.info("Couldn't update Amount bottle in BD");
            throw new RuntimeException(e);
        }
        return "Success Update";
    }

    @Override
    public long findOrderBottles(long orderID, long bottleID) {
        String sql = "SELECT OrderBottleID \n" +
                "FROM orderbottle \n" +
                "WHERE OrderID =" + orderID + " and BottleID =" + bottleID + ";";
        long id = 0;
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);
            while (rs.next()) {
                id = rs.getLong("OrderBottleID");
            }
        } catch (SQLException e) {
            logger.info("Couldn't order find by id in BD");
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public List<BottleListWrapper> getFinalOrder(Order order) {
        List<BottleListWrapper> bottleListDtoList = new ArrayList<>();
        String sql = "select b.BottleID, ob.AmountBottle, b.NameBottle, v.BottleVolume, p.Price, b.CO2, b.Plastic, b.CreateDate \n" +
                "from orders as o \n" +
                "inner join orderbottle as ob on o.OrderID = ob.OrderID \n" +
                "inner join customer c on c.CustomerID = o.CustomerID \n"+
                "inner join bottle as b on b.BottleID = ob.BottleID \n" +
                "inner join price p on p.PriceID = b.PriceID \n" +
                "inner join volume v on v.VolumeID = b.VolumeID \n" +
                "where o.OrderID ="+ order.getOrderID() +" ;";
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);

            while (rs.next()) {
                BottleListWrapper finalOrderDto = new BottleListWrapper();
                finalOrderDto.setBottleID(rs.getLong("BottleID"));
                finalOrderDto.setAmountBottle(rs.getInt("AmountBottle"));
                finalOrderDto.setNameBottle(rs.getString("NameBottle"));
                finalOrderDto.setSize(rs.getDouble("BottleVolume"));
                finalOrderDto.setPrice(rs.getDouble("Price"));
                finalOrderDto.setCO2(rs.getBoolean("CO2"));
                finalOrderDto.setPlastic(rs.getBoolean("Plastic"));
                finalOrderDto.setDateTime(rs.getTimestamp("CreateDate").toLocalDateTime());
                bottleListDtoList.add(finalOrderDto);
            }
        } catch (SQLException e) {
            logger.info("Couldn't get FinalOrder from BD");
            throw new RuntimeException(e);
        }
        return bottleListDtoList;
    }

    @Override
    public InvoiceWrapper getOrderInvoice(Order order) {
        String sql = "select o.OrderID, c.NameCompany, o.DeliveryAddress, o.CurentDate, b.Producer \n" +
                "from orders as o \n" +
                "inner join orderbottle as ob on o.OrderID = ob.OrderID \n" +
                "inner join customer c on c.CustomerID = o.CustomerID \n"+
                "inner join bottle as b on b.BottleID = ob.BottleID \n" +
                "where o.OrderID ="+ order.getOrderID() +" ;";
        InvoiceWrapper orderDto = new InvoiceWrapper();
        try {
            ResultSet rs = getConnectionStatement().executeQuery(sql);

            while (rs.next()) {
                orderDto.setOrderID(rs.getLong("OrderID"));
                orderDto.setNameCompany(rs.getString("NameCompany"));
                orderDto.setDeliveryAddress(rs.getString("DeliveryAddress"));
                orderDto.setCurentDate(rs.getTimestamp("CurentDate").toLocalDateTime());
                orderDto.setProducer(rs.getString("Producer"));
            }
        } catch (SQLException e) {
            logger.info("Couldn't get FinalOrder from BD");
            throw new RuntimeException(e);
        }
        orderDto.setBottleListDtoList(getFinalOrder(order));
        return orderDto;
    }
}
