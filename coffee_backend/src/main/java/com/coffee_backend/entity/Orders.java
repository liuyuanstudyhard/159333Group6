package com.coffee_backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Setter
@Getter
public class Orders {

    // Getter and Setter for orderId
    private Integer orderId;
    // Getter and Setter for date
    private String date;
    // Getter and Setter for time
    private String time;
    // Getter and Setter for address
    private String address;
    // Getter and Setter for specificLocation
    private String price;
    // Getter and Setter for userId
    private String userId;
    // Getter and Setter for orderStatus
    private String orderStatus;

    private String productDetails;

}