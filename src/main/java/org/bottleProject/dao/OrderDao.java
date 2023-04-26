package org.bottleProject.dao;

import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.FullOrderDto;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;

import java.util.List;

public interface OrderDao extends Dao<Order> {
    public List<Order> allCustomerOrder(long id);
    String setOrderBottles(OrderBottle orderBottle);

    Long findOrderBottles(long order, long bottleID);

    List<BottleListWrapper> getFinalOrder(Order order);

    InvoiceWrapper getOrderInvoice(Order order);

    List<Order> searchOrder(OrderSearchDto orderSearchDto);

    Integer countFilterCustomerOrders(int profileId);

    List<FullOrderDto> getAllFilterCustomerOrder(int profileId, int page, int size);

    Integer countSearchCustomerOrder(OrderSearchDto orderSearchDto);

    List<FullOrderDto> getAllFilterOrder(int page, int size);

    Integer countFilterOrders();


    Integer getOrderStatus(String status);

    Integer getOrderAddress(String address);

    List<FullOrderDto> getAllOperatorOrders(String email, int offset, int size);

    Integer countOperatorOrders(String email);

    List<FullOrderDto> searchCustomer(String search);
}
