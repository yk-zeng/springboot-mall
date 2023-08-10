package com.tseng.dao;

import com.tseng.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);
    void createOrderItems(Integer oderId, List<OrderItem> orderItemList);



}
