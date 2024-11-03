package com.coffee_backend.mapper;

import com.coffee_backend.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {


    @Select("SELECT * FROM orders")
    List<Orders> selectAllOrders();


    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    List<Orders> selectByUserId(int userId);


    @Select("SELECT * FROM orders WHERE order_status = #{orderStatus}")
    List<Orders> selectByOrderStatus(String orderStatus);


    @Select("SELECT * FROM orders WHERE date BETWEEN #{startDate} AND #{endDate}")
    List<Orders> selectByDateRange(String startDate, String endDate);


    @Select("SELECT * FROM orders WHERE user_id = #{userId} AND order_status = #{orderStatus}")
    List<Orders> selectByUserIdAndStatus(int userId, String orderStatus);


    @Insert("INSERT INTO orders (date, time, address, price, user_id, order_status, product_details) VALUES (#{date}, #{time}, #{address}, #{price}, #{userId}, #{orderStatus}, #{productDetails})")
    int insertOrder(Orders order);


    @Update("UPDATE orders SET date = #{date}, time = #{time}, address = #{address}, price = #{price}, order_status = #{orderStatus}, product_details = #{productDetails} WHERE order_id = #{orderId}")
    int updateOrder(Orders order);


    @Delete("DELETE FROM orders WHERE order_id = #{orderId}")
    int deleteOrder(int orderId);
}
