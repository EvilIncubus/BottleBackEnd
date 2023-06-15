package org.bottleProject.service.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.*;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;
import org.bottleProject.service.NotificationService;
import org.bottleProject.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.logging.Logger;

@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());

    private final OrderDao orderDao;
    private final NotificationService notificationService;

    public OrderServiceImpl(OrderDao orderDao, NotificationService notificationService) {
        this.orderDao = orderDao;
        this.notificationService = notificationService;
    }

    @Override
    public Page<FullOrderDto> getAllCustomerOrder(ListOfCustomersOrdersDto listOrderDto){
        List<FullOrderDto> orderList = orderDao.getAllFilterCustomerOrder(listOrderDto.getProfileId(), listOrderDto.getOperatorEmail(), listOrderDto.getOffset(), listOrderDto.getSize());
        return new Page<>(orderList, orderDao.countFilterCustomerOrders(listOrderDto.getProfileId()), listOrderDto.getPage(), listOrderDto.getSize());
    }

    @Override
    public Page<FullOrderDto> getAllOrder(int page, int size) {
        List<FullOrderDto> orderList = orderDao.getAllFilterOrder((page - 1) * size, size);
        return new Page<>(orderList, orderDao.countFilterOrders(), page, size);
    }

    @Override
    public FullOrderDto createOrder(CreateOrderDto order) {
        Order orderToDB = new Order();
        orderToDB.setProfileId(order.getProfileId());
        orderToDB.setStatusId(orderDao.getOrderStatus(order.getStatus()));
        orderToDB.setDeliveryAddressId(orderDao.getOrderAddress(order.getAddress()));
        orderToDB.setCreatedDate(LocalDateTime.now());
        orderToDB.setOperatorEmail(order.getOperatorEmail());
        Order orderResponse = orderDao.create(orderToDB);
        return orderDao.getFullOrder(orderResponse.getOrderId());
    }

    @Override
    public String addItemToOrder(OrderBottle orderBottle) {
        orderDao.removeBottlesFromStore(orderBottle.getAmountBottle(), orderBottle.getBottleId());
        return orderDao.setOrderBottles(orderBottle);
    }

    @Override
    public InvoiceWrapper getOrderById(long orderId) {
        Order order = orderDao.findById(orderId);
        return orderDao.getOrderInvoice(order);
    }

    @Override
    public Page<FullOrderDto> filterOrder(OrderFilterDto orderSearchDto) {
        return new Page<>(orderDao.filterOrders(orderSearchDto),orderDao.countFilterCustomerOrder(orderSearchDto), orderSearchDto.getPage(), orderSearchDto.getSize());
    }

    @Override
    public Page<FullOrderDto> getListOfOperatorsOrders(CustomersQueryForOperatorDto customersQueryForOperator) {
        List<FullOrderDto> orderList = orderDao.getAllOperatorOrders(customersQueryForOperator.getEmail(), customersQueryForOperator.getOffset(), customersQueryForOperator.getSize());
        return new Page<>(orderList, orderDao.countOperatorOrders(customersQueryForOperator.getEmail()), customersQueryForOperator.getPage(), customersQueryForOperator.getSize());
    }

    @Override
    public Page<FullOrderDto> searchOrder(OrderSearchDto searchOrderDto) {
        return new Page<>(orderDao.searchOrders(searchOrderDto),orderDao.countSearchCustomerOrder(searchOrderDto), searchOrderDto.getPage(), searchOrderDto.getSize());
    }

    @Override
    public Page<FullOrderDto> filterAllOrder(OrderAllFilterDto filterOrderDto) {
        return new Page<>(orderDao.filterAllOrders(filterOrderDto),orderDao.countFilterAllOrder(filterOrderDto), filterOrderDto.getPage(), filterOrderDto.getSize());
    }

    @Override
    public Page<FullOrderDto> searchAllOrder(OrderSearchAllDto searchOrderDto) {
        return new Page<>(orderDao.searchAllOrders(searchOrderDto),orderDao.countSearchAllOrder(searchOrderDto), searchOrderDto.getPage(), searchOrderDto.getSize());
    }

    @Override
    public List<String> getDeliveryAddress() {
        return orderDao.getDeliveryAddress();
    }

    @Override
    public List<String> getSearchDeliveryAddress(String search) {
        return orderDao.getSearchDeliveryAddress(search);
    }

    @Override
    public Double countYesterdayAmount(String operatorEmail) {
        LocalDate yesterday = LocalDate.now().minus(1, ChronoUnit.DAYS);
        LocalDateTime start = LocalTime.MIN.atDate(yesterday);
        LocalDateTime end = LocalTime.MAX.atDate(yesterday);
        logger.info(start + "  " + end);
        List<Double> amounts = orderDao.countYesterdayAmount(operatorEmail, start, end);
        double count = 0;
        for(Double amount : amounts){
            count = count + amount;
        }
        return count;
    }

    @Override
    public Double countAllYesterdayAmount() {
        LocalDate yesterday = LocalDate.now().minus(1, ChronoUnit.DAYS);
        LocalDateTime start = LocalTime.MIN.atDate(yesterday);
        LocalDateTime end = LocalTime.MAX.atDate(yesterday);
        logger.info(start + "  " + end);
        List<Double> amounts = orderDao.countAllYesterdayAmount(start, end);
        double count = 0;
        for(Double amount : amounts){
            count = count + amount;
        }
        return count;
    }

    @Override
    public String setBottleStatus(OrderBottle orderBottle) {
        return orderDao.setBottleStatus(orderBottle);
    }

    @Override
    public String submitCreatedOrderByCustomer(int orderId, String status) {
        orderDao.updateOrderStatus(orderId, status);
        String message = "Order Status Updated!";
        notificationService.createNotification(orderId, status, message);
        return message;
    }


}
