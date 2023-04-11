package org.bottleProject.service.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.CreateOrderDto;
import org.bottleProject.dto.FullOrderDto;
import org.bottleProject.dto.ListOrderDto;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;
import org.bottleProject.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Page<Order> getAllCustomerOrder(ListOrderDto listOrderDto){
        List<Order> orderList = orderDao.getAllFilterCustomerOrder(listOrderDto.getOrderId(), listOrderDto.getOffset(), listOrderDto.getSize());
        return new Page<>(orderList, orderDao.countFilterCustomerOrders(listOrderDto.getOrderId()), listOrderDto.getPage(), listOrderDto.getSize());
    }

    @Override
    public Page<FullOrderDto> getAllOrder(int page, int size) {
        List<FullOrderDto> orderList = orderDao.getAllFilterOrder((page - 1) * size, size);
        return new Page<>(orderList, orderDao.countFilterOrders(), page, size);
    }

    @Override
    public long createOrder(CreateOrderDto order) {
        Order orderToDB = new Order();
        orderToDB.setProfileId(order.getProfileId());
        orderToDB.setStatusId(orderDao.getOrderStatus(order.getStatus()));
        orderToDB.setAddressId(orderDao.getOrderAddress(order.getAddress()));
        orderToDB.setCreatedDate(LocalDateTime.now());
        Order orderResponse = orderDao.create(orderToDB);
        return orderResponse.getOrderId();
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
        return new Page<>(orderDao.searchOrder(orderSearchDto),orderDao.countSearchCustomerOrder(orderSearchDto), orderSearchDto.getPage(), orderSearchDto.getSize());
    }


}
