package org.bottleProject.controller;

import org.bottleProject.dto.ListOrderDto;
import org.bottleProject.dto.OrderSearchDto;
import org.bottleProject.entity.Bottle;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;
import org.bottleProject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/customer/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Order> createOrderByCustomerId(@RequestBody Order order) {
        try {
            Order orderResponse = orderService.createOrder(order);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addItemToOrder")
    public ResponseEntity<String> addItemToOrder(@RequestBody OrderBottle orderBottle) {
        try {
            String info = orderService.addItemToOrder(orderBottle);
            return new ResponseEntity<>(info, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error invoice creating.", HttpStatus.INTERNAL_SERVER_ERROR);
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

    @PostMapping("/getListOfOrders")
    public ResponseEntity<Page<Order>> getListOfOrders(@RequestBody ListOrderDto listOrderDto) {
        try {
            Page<Order> orders = orderService.getAllOrder(listOrderDto);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/searchOrder")
    public ResponseEntity<Page<Order>> searchOrder(@RequestBody OrderSearchDto searchOrderDto) {
        try {
            Page<Order> orders = orderService.searchOrder(searchOrderDto);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
