package org.bottleProject.service.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.BottleListWrapper;
import org.bottleProject.dto.InvoiceWrapper;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Order;
import org.bottleProject.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao<Order> orderDao;

    public OrderServiceImpl(OrderDao<Order> orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order addBottlesToOrder(Order order) {
        long idOrder;
        if (order.getOrderID() == 0L) {
            idOrder = orderDao.create(order);
        }
        else {
            idOrder = order.getOrderID();
        }
        List<Bottle> bottles = order.getBottles();
        for (Bottle bottle : bottles) {
            long id = orderDao.findOrderBottles(idOrder, bottle.getBottleID());
            if (id == 0) {
                orderDao.setOrderBottles(idOrder, bottle.getBottleID());
            } else {
                orderDao.updateOrderBottles(id);
            }
        }
        return order;
    }
    @Override
    public List<Order> getAllOrder(Order order){
        return orderDao.allCustomerOrder(order.getCustomerID());
    }
    @Override
    public void removeBottlesFromOrder(Order order) {
        //TODO to implement
    }

    @Override
    public void removeOrder(Order order) {
        orderDao.removeById(order.getOrderID());
    }

    //
    @Override
    public void prepareInvoice(Order order) {
        InvoiceWrapper invoiceWrapper = orderDao.getOrderInvoice(order);
        InvoicingServiceImpl invoicingService = new InvoicingServiceImpl();
        invoicingService.invoicing(invoiceWrapper);
    }

}
