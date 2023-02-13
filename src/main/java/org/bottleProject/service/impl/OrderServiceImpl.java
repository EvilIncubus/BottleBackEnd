package org.bottleProject.service.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.ListOrderDto;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;
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
    public Page<Order> getAllOrder(ListOrderDto listOrderDto){
        List<Order> orderList = orderDao.getAllFilterOrder(listOrderDto.getNameCompany(), listOrderDto.getOffset(), listOrderDto.getSize());
        return new Page<>(orderList, orderDao.countFilterOrders(listOrderDto.getNameCompany()), listOrderDto.getPage(), listOrderDto.getSize());
    }

    @Override
    public Order createOrder(Order order) {
        return orderDao.create(order);
    }

    @Override
    public String addItemToOrder(OrderBottle orderBottle) {
        return orderDao.setOrderBottles(orderBottle);
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderDao.findById(orderId);
    }

    @Override
    public Page<Order> searchOrder(OrderSearchDto orderSearchDto) {
        return new Page<>(orderDao.searchOrder(orderSearchDto),orderDao.countSearchOrder(orderSearchDto), orderSearchDto.getPage(), orderSearchDto.getSize());
    }


}
