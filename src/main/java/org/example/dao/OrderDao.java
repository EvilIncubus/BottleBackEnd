package org.example.dao;

import org.example.dto.BottleListWrapper;
import org.example.dto.InvoiceWrapper;

import java.util.List;

public interface OrderDao<Order> extends Dao<Order> {
    String setOrderBottles(long order, long bottle);
    String updateOrderBottles(long id);

    long findOrderBottles(long order, long bottleID);

    List<BottleListWrapper> getFinalOrder(Order order);

    InvoiceWrapper getOrderInvoice(Order order);
}
