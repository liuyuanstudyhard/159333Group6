package com.coffee_backend.service.impl;

import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Orders;
import com.coffee_backend.mapper.OrdersMapper;
import com.coffee_backend.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Override
    public Result<Orders> addOrder(Orders order) {
        boolean save = this.save(order);
        if (save) {
            return new Result<>(200, "Order added successfully", order);
        } else {
            return new Result<>(500, "Failed to add order", null);
        }
    }

    @Override
    public Result<Boolean> deleteOrder(Long id) {
        boolean remove = this.removeById(id);
        if (remove) {
            return new Result<>(200, "Order deleted successfully", true);
        } else {
            return new Result<>(404, "Order not found", false);
        }
    }

    @Override
    public Result<Orders> updateOrder(Orders order) {
        boolean update = this.updateById(order);
        if (update) {
            return new Result<>(200, "Order updated successfully", order);
        } else {
            return new Result<>(404, "Order not found", null);
        }
    }

    @Override
    public Result<Orders> getOrderById(Long id) {
        Orders order = this.getById(id);
        if (order != null) {
            return new Result<>(200, "Order found", order);
        } else {
            return new Result<>(404, "Order not found", null);
        }
    }

    @Override
    public Result<List<Orders>> getAllOrders() {
        List<Orders> orders = this.list();
        return new Result<>(200, "Orders retrieved successfully", orders);
    }

    @Override
    public List<Orders> getOrdersByUserId(int userId) {
        return this.listByIds(List.of(userId));
    }

    @Override
    public void updateOrderStatus(int orderId, String orderStatus) {
        Orders order = this.getById(orderId);
        order.setOrderStatus(orderStatus);
        this.updateById(order);
    }
}
