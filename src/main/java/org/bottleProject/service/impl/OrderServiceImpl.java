package org.bottleProject.service.impl;

import org.bottleProject.dao.OrderDao;
import org.bottleProject.dto.InvoiceWrapper;
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
            long id = orderDao.findOrderBottles(idOrder, bottle.getBottleId());
            if (id == 0) {
                orderDao.setOrderBottles(idOrder, bottle.getBottleId());
            } else {
                orderDao.updateOrderBottles(id);
            }
        }
        return order;
    }
    //todo naming enter params
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
