package org.example.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.example.dao.OrderDao;
import org.example.dto.BottleListWrapper;
import org.example.dto.InvoiceWrapper;
import org.example.entity.Bottle;
import org.example.entity.Order;
import org.example.service.OrderService;
import org.example.util.FileManager;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderDao<Order> orderDao;

    public OrderServiceImpl(OrderDao<Order> orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order addBottlesToOrder(Order order, List<Bottle> bottles) {
        long idOrder;
        if (order.getOrderID() == 0L) {
            idOrder = orderDao.create(order);
        }
        else {
            idOrder = order.getOrderID();
        }
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
    public void removeBottlesFromOrder(Order order) {
        //TODO to implement
    }

    @Override
    public void removeOrder(Order order) {
        //TODO to implement
    }

    //
    @Override
    public BottleListWrapper prepareInvoice(Order order) {
        InvoiceWrapper invoiceWrapper = orderDao.getOrderInvoice(order);
        InvoicingServiceImpl invoicingService = new InvoicingServiceImpl();
        invoicingService.invoicing(invoiceWrapper);
        return null;
    }

}
