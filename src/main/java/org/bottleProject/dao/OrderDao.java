package org.bottleProject.dao;

import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.dto.SearchOrderDto;
import org.bottleProject.entity.Order;

import java.util.List;

public interface OrderDao extends Dao<Order> {
    public List<Order> allCustomerOrder(long id);
    String setOrderBottles(long order, long bottle);
    String updateOrderBottles(long id);

    Long findOrderBottles(long order, long bottleID);

    List<BottleListWrapper> getFinalOrder(Order order);

    InvoiceWrapper getOrderInvoice(Order order);

    List<Order> searchOrder(SearchOrderDto searchOrderDto);

}
