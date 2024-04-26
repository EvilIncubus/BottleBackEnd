package org.bottleProject.controller;

import org.bottleProject.dto.*;
import org.bottleProject.entity.OrderBottle;
import org.bottleProject.entity.Page;
import org.bottleProject.service.MailSendingService;
import org.bottleProject.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rest/api/customer/order")
public class OrderController {
    private final OrderService orderService;
    private final MailSendingService mailSendingService;

    private final static Logger logger = Logger.getLogger(OrderController.class.getName());

    public OrderController(OrderService orderService, MailSendingService mailSendingService) {
        this.orderService = orderService;
        this.mailSendingService = mailSendingService;
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR') or hasAnyAuthority('MANAGER')")
    @PostMapping("/createOrder")
    public ResponseEntity<FullOrderDto> createOrderByCustomerId(@RequestBody CreateOrderDto order) {
        try {
            FullOrderDto orderResponse = orderService.createOrder(order);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
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
            logger.info(e.toString());
            return new ResponseEntity<>("error invoice creating.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('OPERATOR')")
    @PostMapping("/setBottleStatus")
    public ResponseEntity<String> setBottleStatus(@RequestBody OrderBottle orderBottle) {
        try {
            String info = orderService.setBottleStatus(orderBottle);
            return new ResponseEntity<>(info, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>("error invoice creating.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @PostMapping("/submitOrderStatus")
    public ResponseEntity<String> submitOrderStatus(@RequestBody MailSendingDto mailSendingDto) {
        try {
            mailSendingService.submitCreatedOrderByCustomer(mailSendingDto);
            return new ResponseEntity<>("successful updated status", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/setOrderConfirmation/{orderId}/{status}")
    public ResponseEntity<String> submitOrderStatus(@PathVariable String orderId, @PathVariable String status) {
        try {
            int setOrderId = Integer.parseInt(orderId);
            String info = orderService.submitCreatedOrderByCustomer(setOrderId, status);
            return new ResponseEntity<>(info, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>("error submit order.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasAnyAuthority('MANAGER') or hasAnyAuthority('OPERATOR') or hasAnyAuthority('STOREMAN') or hasAnyAuthority('SHIPPER')")
    @GetMapping("/getOrderById")
    public ResponseEntity<InvoiceWrapper> getOrderById(@RequestParam long orderId) {
        try {

             InvoiceWrapper order = orderService.getOrderById(orderId);

            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //todo get-operator-orders
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
            logger.info(e.toString());
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
            logger.info(e.toString());
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
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping("/filterOrderForManger")
    public ResponseEntity<Page<FullOrderDto>> filterOrder(@RequestBody OrderAllFilterDto filterOrderDto) {
        try {
            Page<FullOrderDto> orders = orderService.filterAllOrder(filterOrderDto);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping("/searchOrderForManger")
    public ResponseEntity<Page<FullOrderDto>> filterOrder(@RequestBody OrderSearchAllDto searchOrderDto) {
        try {
            Page<FullOrderDto> orders = orderService.searchAllOrder(searchOrderDto);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR') or hasAnyAuthority('STOREMAN') or hasAnyAuthority('SHIPPER')")
    @PostMapping("/filterOrderForOperator")
    public ResponseEntity<Page<FullOrderDto>> filterOrderForOperator(@RequestBody OrderFilterDto searchOrderDto) {
        try {
            Page<FullOrderDto> orders = orderService.filterOrder(searchOrderDto);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//todo when do refactor get rol from authorization
    @PreAuthorize("hasAnyAuthority('OPERATOR') or hasAnyAuthority('STOREMAN')")
    @PostMapping("/searchOrderForOperator")
    public ResponseEntity<Page<FullOrderDto>> searchOrderForOperator(@RequestBody OrderSearchDto searchOrderDto) {
        try {
            Page<FullOrderDto> orders = orderService.searchOrder(searchOrderDto);

            if (orders == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @GetMapping("/getSalesForYesterday")
    public ResponseEntity<Double> getSalesForYesterday(@RequestParam String operatorEmail) {
        try {
            Double yesterdayAmount = orderService.countYesterdayAmount(operatorEmail);

            if (yesterdayAmount == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(yesterdayAmount, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping("/getAllSalesForYesterday")
    public ResponseEntity<Double> getAllSalesForYesterday() {
        try {
            Double yesterdayAmount = orderService.countAllYesterdayAmount();

            if (yesterdayAmount == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(yesterdayAmount, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @GetMapping("/getListOfAddressForOperator")
    public ResponseEntity<List<String>> getListOfAddressForOperator() {
        try {
            List<String> address = orderService.getDeliveryAddress();

            if (address == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyAuthority('OPERATOR')")
    @GetMapping("/getSearchListOfAddressForOperator")
    public ResponseEntity<List<String>> getSearchListOfAddressForOperator(@RequestParam String search) {
        try {
            List<String> address = orderService.getSearchDeliveryAddress(search);

            if (address == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (Exception e) {
            logger.info(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
