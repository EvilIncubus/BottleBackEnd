package org.bottleProject.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.bottleProject.dto.SearchOrderDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Order;

import java.util.List;

public interface OrderService {

    public List<Order> getAllOrder(int customerId);

    void createOrder(Order order);

    void addItemToOrder(List<Bottle> bottles, long orderId);

    Order getOrderById(long orderId);

    List<Order> searchOrder(SearchOrderDto searchOrderDto);
}
