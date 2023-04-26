package org.bottleProject.service.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.dto.*;
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
    private final UserDao userDao;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
    }

    @Override
    public Page<FullOrderDto> getAllCustomerOrder(ListOfCustomersOrdersDto listOrderDto){
        List<FullOrderDto> orderList = orderDao.getAllFilterCustomerOrder(listOrderDto.getProfileId(), listOrderDto.getOffset(), listOrderDto.getSize());
        return new Page<>(orderList, orderDao.countFilterCustomerOrders(listOrderDto.getProfileId()), listOrderDto.getPage(), listOrderDto.getSize());
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
        orderToDB.setDeliveryAddressId(orderDao.getOrderAddress(order.getAddress()));
        orderToDB.setCreatedDate(LocalDateTime.now());
        orderToDB.setOperatorEmail(order.getOperatorEmail());
        Order orderResponse = orderDao.create(orderToDB);
        return orderResponse.getOrderId();
    }

    @Override
    public String addItemToOrder(OrderBottle orderBottle) {
        return orderDao.setOrderBottles(orderBottle);
    }

    @Override
    public InvoiceWrapper getOrderById(long orderId) {
        Order order = orderDao.findById(orderId);
        return orderDao.getOrderInvoice(order);
    }

    @Override
    public Page<Order> searchOrder(OrderSearchDto orderSearchDto) {
        return new Page<>(orderDao.searchOrder(orderSearchDto),orderDao.countSearchCustomerOrder(orderSearchDto), orderSearchDto.getPage(), orderSearchDto.getSize());
    }

    @Override
    public Page<FullOrderDto> getListOfOperatorsOrders(CustomersQueryForOperatorDto customersQueryForOperator) {
        List<FullOrderDto> orderList = orderDao.getAllOperatorOrders(customersQueryForOperator.getEmail(), customersQueryForOperator.getOffset(), customersQueryForOperator.getSize());
        return new Page<>(orderList, orderDao.countOperatorOrders(customersQueryForOperator.getEmail()), customersQueryForOperator.getPage(), customersQueryForOperator.getSize());
    }

    @Override
    public Page<FullOrderDto> getAllFilterCustomerOrder(String search) {
        return new Page<>(orderDao.searchCustomer(search),5, 0, 5);
    }


}
