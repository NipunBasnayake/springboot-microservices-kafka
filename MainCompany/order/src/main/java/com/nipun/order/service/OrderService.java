package com.nipun.order.service;

import com.nipun.order.dto.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order updateOrder(Long id, Order updatedOrder);
    void deleteOrder(Long id);
}
