package com.order.order.service;

import com.order.order.common.OrderResponse;
import com.order.order.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    OrderResponse saveOrder(OrderDTO order);

    OrderDTO updateOrder(OrderDTO order);

    String deleteOrder(Integer orderId);

    OrderDTO getOrderById(Integer orderId);
}
