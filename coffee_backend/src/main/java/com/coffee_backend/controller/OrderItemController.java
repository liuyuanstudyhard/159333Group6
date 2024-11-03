package com.coffee_backend.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.coffee_backend.entity.Orderitems;
import com.coffee_backend.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("orderitem")
public class OrderItemController {
    @Autowired
    private OrderItemsService orderItemsService;


    @PostMapping("/add")
    public void addOrderItem(@RequestParam int orderId,
                             @RequestParam int coffeeId,
                             @RequestParam String size,
                             @RequestParam int quantity) {
        Orderitems orderItems = new Orderitems();
        orderItems.setOrderId(orderId);
        orderItems.setCoffeeId(coffeeId);
        orderItems.setSize(size);
        orderItems.setQuantity(quantity);
        orderItemsService.addOrderItem(orderItems);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteOrderItem(@PathVariable int id) {
        orderItemsService.deleteOrderItem((long)id);
    }

    @GetMapping("/all")
    public List<Orderitems> getAllOrderItems() {
        return orderItemsService.getAllOrderItems().getData();
    }

    @PutMapping("/update/{id}")
    public void updateOrderItem(@PathVariable int id, @RequestParam int orderId,
                                @RequestParam int coffeeId, @RequestParam String size,
                                @RequestParam int quantity) {
        Orderitems orderItems = new Orderitems();
        orderItems.setId(id);
        orderItems.setOrderId(orderId);
        orderItems.setCoffeeId(coffeeId);
        orderItems.setSize(size);
        orderItems.setQuantity(quantity);
        orderItemsService.updateOrderItem(orderItems);
    }

    @GetMapping("/by-order/{orderId}")
    public List<Orderitems> getOrderItemsByOrderId(@PathVariable int orderId) {
        return orderItemsService.getOrderItemsByOrderId(orderId);
    }
}
