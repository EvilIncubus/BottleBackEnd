package org.example.service;

import org.example.entity.Order;

public interface ExecuteOrderService {
    Order assignOrderToStoreman(Order order);
    Order editStatusOrder(Order order);
}
