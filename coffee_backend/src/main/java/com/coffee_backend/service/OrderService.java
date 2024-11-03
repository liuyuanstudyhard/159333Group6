package com.coffee_backend.service;

import com.coffee_backend.entity.Orders;
import com.coffee_backend.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrdersMapper ordersMapper;


    public List<Orders> getAllOrders() {
        return ordersMapper.selectAllOrders();
    }


    public List<Orders> getOrdersByUserId(int userId) {
        return ordersMapper.selectByUserId(userId);
    }


    public List<Orders> getOrdersByStatus(String orderStatus) {
        return ordersMapper.selectByOrderStatus(orderStatus);
    }


    public List<Orders> getOrdersByDateRange(String startDate, String endDate) {
        return ordersMapper.selectByDateRange(startDate, endDate);
    }


    public List<Orders> getOrdersByUserIdAndStatus(int userId, String orderStatus) {
        return ordersMapper.selectByUserIdAndStatus(userId, orderStatus);
    }


    public int addOrder(Orders order) {
        return ordersMapper.insertOrder(order);
    }


    public int updateOrder(Orders order) {
        return ordersMapper.updateOrder(order);
    }


    public int deleteOrder(int orderId) {
        return ordersMapper.deleteOrder(orderId);
    }
}
