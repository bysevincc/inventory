package com.airheads.order.service;

import com.airheads.order.entity.Order;

public interface OrderService {
    Iterable<Order> getAllOrders();

    Order create(Order order);

    void update(Order order);
}
