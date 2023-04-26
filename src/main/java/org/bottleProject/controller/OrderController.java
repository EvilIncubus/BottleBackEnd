package org.bottleProject.controller;

import org.bottleProject.dto.*;
import org.bottleProject.entity.Order;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;
import org.bottleProject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/customer/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR') or hasAnyAuthority('MANAGER')")
    @PostMapping("/createOrder")
    public ResponseEntity<Long> createOrderByCustomerId(@RequestBody CreateOrderDto order) {
        try {
            long orderResponse = orderService.createOrder(order);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('OPERATOR')")
    @PostMapping("/addItemToOrder")
    public ResponseEntity<String> addItemToOrder(@RequestBody OrderBottle orderBottle) {
        try {
            String info = orderService.addItemToOrder(orderBottle);
            return new ResponseEntity<>(info, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error invoice creating.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('OPERATOR')")
    @GetMapping("/getOrderById")
    public ResponseEntity<InvoiceWrapper> getOrderById(@RequestParam long orderId) {
        try {

            InvoiceWrapper order = orderService.getOrderById(orderId);

            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @PostMapping("/getListOfActiveOrdersForOperator")
    public ResponseEntity<Page<FullOrderDto>> getListOfActiveOrders(@RequestBody ListOfCustomersOrdersDto listOrderDto) {
        try {
            Page<FullOrderDto> orders = orderService.getAllCustomerOrder(listOrderDto);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @PostMapping("/getSearchListOfActiveOrdersForOperator")
    public ResponseEntity<Page<FullOrderDto>> getSearchListOfCustomer(@RequestParam String search) {
        try {
            Page<FullOrderDto> orders = orderService.getAllFilterCustomerOrder(search);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @PostMapping("/getListOfOrdersForOperator")
    public ResponseEntity<Page<FullOrderDto>> getListOfOperatorsOrders(@RequestBody CustomersQueryForOperatorDto customersQueryForOperator) {
        try {
            Page<FullOrderDto> orders = orderService.getListOfOperatorsOrders(customersQueryForOperator);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @PostMapping("/getListOfCustomersOrdersForOperator")
    public ResponseEntity<Page<FullOrderDto>> getListOfOrdersOfCustomer(@RequestBody ListOfCustomersOrdersDto listOrderDto) {
        try {
            Page<FullOrderDto> orders = orderService.getAllCustomerOrder(listOrderDto);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping("/getListOfOrdersForManager")
    public ResponseEntity<Page<FullOrderDto>> getListOfOrdersForManager(@RequestParam int page,
                                                                 @RequestParam int size) {
        try {
            Page<FullOrderDto> orders = orderService.getAllOrder(page, size);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('ORDER')")
    @PostMapping("/searchOrderForManger")
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

    @PreAuthorize("hasAnyAuthority('ORDER')")
    @PostMapping("/searchOrderForOperator")
    public ResponseEntity<Page<Order>> searchOrderForOperator(@RequestBody OrderSearchDto searchOrderDto) {
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
