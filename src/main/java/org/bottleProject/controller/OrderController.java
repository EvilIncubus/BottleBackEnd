package org.bottleProject.controller;

import org.bottleProject.entity.Order;
import org.bottleProject.service.OrderService;
import org.bottleProject.service.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/newOrder")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        try {
            orderService.addBottlesToOrder(order);
            return new ResponseEntity<>("Order was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //todo request should be pageable
    @PostMapping("/createInvoice")
    public ResponseEntity<String> createInvoice(@RequestBody Order order) {
        try {
            orderService.prepareInvoice(order);
            return new ResponseEntity<>("invoice was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/allOrders/{id}")
    public ResponseEntity<List<Order>> findByPublished(@PathVariable int id) {
        try {
            Order order = new Order();
            order.setCustomerID(id);
            List<Order> orders = orderService.getAllOrder(order);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
