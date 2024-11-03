package com.coffee_backend.service;

import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface OrdersService extends IService<Orders> {
    Result<Orders> addOrder(Orders order);
    Result<Boolean> deleteOrder(Long id);
    Result<Orders> updateOrder(Orders order);
    Result<Orders> getOrderById(Long id);
    Result<List<Orders>> getAllOrders();
    List<Orders> getOrdersByUserId(int userId);
    void updateOrderStatus(int orderId, String orderStatus);
}
