package org.bottleProject.controller;

import org.bottleProject.dto.SearchOrderDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Customer;
import org.bottleProject.entity.Order;
import org.bottleProject.service.OrderService;
import org.bottleProject.service.impl.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rest/api/customer/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrderByCustomerId(@RequestBody Order order) {
        try {
            orderService.createOrder(order);
            return new ResponseEntity<>("Order was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addItemToOrder")
    public ResponseEntity<String> addItemToOrder(@RequestBody List<Bottle> bottles, long orderId) {
        try {
            orderService.addItemToOrder(bottles, orderId);
            return new ResponseEntity<>("invoice was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getOrderById/{customerId}")
    public ResponseEntity<Order> getOrderById(@PathVariable long orderId) {
        try {

            Order order = orderService.getOrderById(orderId);

            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getListOfOrders/{customerId}")
    public ResponseEntity<List<Order>> getListOfOrders(@PathVariable int customerId) {
        try {
            List<Order> orders = orderService.getAllOrder(customerId);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/searchOrder/{customerId}")
    public ResponseEntity<List<Order>> searchOrder(@PathVariable SearchOrderDto searchOrderDto) {
        try {
            List<Order> orders = orderService.searchOrder(searchOrderDto);

            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
