package org.bottleProject.dao;

import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;

import java.util.List;

public interface OrderDao extends Dao<Order> {
    public List<Order> allCustomerOrder(long id);
    String setOrderBottles(OrderBottle orderBottle);

    Long findOrderBottles(long order, long bottleID);

    List<BottleListWrapper> getFinalOrder(Order order);

    InvoiceWrapper getOrderInvoice(Order order);

    List<Order> searchOrder(OrderSearchDto orderSearchDto);

    Integer countFilterOrders(int orderId);

    List<Order> getAllFilterOrder(int orderId, int page, int size);

    Integer countSearchOrder(OrderSearchDto orderSearchDto);
}
