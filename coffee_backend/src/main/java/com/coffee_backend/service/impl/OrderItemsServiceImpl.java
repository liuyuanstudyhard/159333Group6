package com.coffee_backend.service.impl;

import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Orderitems;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coffee_backend.mapper.OrderItemsMapper;
import com.coffee_backend.service.OrderItemsService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderItemsServiceImpl extends ServiceImpl<OrderItemsMapper, Orderitems> implements OrderItemsService {
    @Override
    public Result<Orderitems> addOrderItem(Orderitems orderItem) {
        boolean save = this.save(orderItem);
        if (save) {
            return new Result<>(200, "Order item added successfully", orderItem);
        } else {
            return new Result<>(500, "Failed to add order item", null);
        }
    }

    @Override
    public Result<Boolean> deleteOrderItem(Long id) {
        boolean remove = this.removeById(id);
        if (remove) {
            return new Result<>(200, "Order item deleted successfully", true);
        } else {
            return new Result<>(404, "Order item not found", false);
        }
    }

    @Override
    public Result<Orderitems> updateOrderItem(Orderitems orderItem) {
        boolean update = this.updateById(orderItem);
        if (update) {
            return new Result<>(200, "Order item updated successfully", orderItem);
        } else {
            return new Result<>(404, "Order item not found", null);
        }
    }

    @Override
    public Result<Orderitems> getOrderItemById(Long id) {
        Orderitems orderItem = this.getById(id);
        if (orderItem != null) {
            return new Result<>(200, "Order item found", orderItem);
        } else {
            return new Result<>(404, "Order item not found", null);
        }
    }

    @Override
    public Result<List<Orderitems>> getAllOrderItems() {
        List<Orderitems> orderItems = this.list();
        return new Result<>(200, "Order items retrieved successfully", orderItems);
    }

    @Override
    public List<Orderitems> getOrderItemsByOrderId(int orderId) {
        return this.listByIds(List.of(orderId));
    }
}
