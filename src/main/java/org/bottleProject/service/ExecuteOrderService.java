package org.bottleProject.service;

import org.bottleProject.entity.Order;

public interface ExecuteOrderService {
    Order assignOrderToStoreman(Order order);
    Order editStatusOrder(Order order);
}
