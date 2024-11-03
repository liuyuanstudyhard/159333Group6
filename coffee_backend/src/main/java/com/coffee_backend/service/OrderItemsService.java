package com.coffee_backend.service;

import com.coffee_backend.common.Result;
import com.coffee_backend.entity.Orderitems;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface OrderItemsService extends IService<Orderitems> {
    Result<Orderitems> addOrderItem(Orderitems orderItem);
    Result<Boolean> deleteOrderItem(Long id);
    Result<Orderitems> updateOrderItem(Orderitems orderItem);
    Result<Orderitems> getOrderItemById(Long id);
    Result<List<Orderitems>> getAllOrderItems();
    List<Orderitems> getOrderItemsByOrderId(int orderId);
}
