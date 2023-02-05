package org.bottleProject.service.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.SearchOrderDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Order;
import org.bottleProject.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> getAllOrder(int customerId){
        return orderDao.allCustomerOrder(customerId);
    }

    @Override
    public void createOrder(Order order) {
        orderDao.create(order);
    }

    @Override
    public void addItemToOrder(List<Bottle> bottles, long orderId) {
        for (Bottle bottle : bottles) {
            long id = orderDao.findOrderBottles(orderId, bottle.getBottleId());
            if (id == 0) {
                orderDao.setOrderBottles(orderId, bottle.getBottleId());
            } else {
                orderDao.updateOrderBottles(id);
            }
        }
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public List<Order> searchOrder(SearchOrderDto searchOrderDto) {
        return orderDao.searchOrder(searchOrderDto);
    }


}
