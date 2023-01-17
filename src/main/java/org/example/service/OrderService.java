package org.example.service;

import org.example.dto.BottleListWrapper;
import org.example.entity.Bottle;
import org.example.entity.Order;

import java.util.List;

public interface OrderService {

    Order addBottlesToOrder(Order order, List<Bottle> bottles);

    void removeBottlesFromOrder(Order order);

    void removeOrder(Order order);

    BottleListWrapper prepareInvoice(Order order);


}
