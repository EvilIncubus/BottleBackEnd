package org.bottleProject.service;

import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Order;

import java.util.List;

public interface OrderService {

    public List<Order> getAllOrder(Order order);

    Order addBottlesToOrder(Order order);

    void removeBottlesFromOrder(Order order);

    void removeOrder(Order order);

    void prepareInvoice(Order order);


}
