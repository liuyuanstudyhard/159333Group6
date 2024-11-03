package com.coffee_backend.controller;


import com.coffee_backend.entity.Orders;
import com.coffee_backend.service.OrderService;
import com.coffee_backend.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService ordersService;

    @PostMapping("/add")
    public void addOrder(@RequestBody Orders orders) {

        ordersService.addOrder(orders);
    }

    @PutMapping("/update/{orderId}")
    public void updateOrder(@PathVariable int orderId, @RequestParam String date,
                            @RequestParam String time, @RequestParam String address,
                            @RequestParam String price, @RequestParam int userId,
                            @RequestParam String orderStatus,
                            @RequestParam String productDetails) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setDate(date);
        orders.setTime(time);
        orders.setAddress(address);
        orders.setPrice(price);
        orders.setUserId(String.valueOf(userId));
        orders.setOrderStatus(orderStatus);
        orders.setProductDetails(productDetails);
        ordersService.updateOrder(orders);
    }

    @GetMapping("/all")
    public List<Orders> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/user/{userId}")
    public List<Orders> getOrdersByUserId(@PathVariable int userId) {
        if (userId==999){
            return ordersService.getAllOrders();
        }

        return ordersService.getOrdersByUserId(userId);
    }


    @DeleteMapping("/delete/{orderId}")
    public void deleteOrder(@PathVariable int orderId) {
        ordersService.deleteOrder( orderId);
    }

}
