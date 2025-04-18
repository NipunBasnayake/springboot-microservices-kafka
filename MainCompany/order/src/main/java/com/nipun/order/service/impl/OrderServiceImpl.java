package com.nipun.order.service.impl;

import com.nipun.order.dto.Order;
import com.nipun.order.entity.OrderEntity;
import com.nipun.order.repository.OrderRepository;
import com.nipun.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Order order) {
        OrderEntity entity = modelMapper.map(order, OrderEntity.class);
        return modelMapper.map(orderRepository.save(entity), Order.class);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, Order.class))
                .collect(Collectors.toList());
    }

    @Override
    public Order getOrderById(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return modelMapper.map(entity, Order.class);
    }

    @Override
    public Order updateOrder(Long id, Order updatedOrder) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        entity.setOrderNumber(updatedOrder.getOrderNumber());
        entity.setProductCode(updatedOrder.getProductCode());
        entity.setQuantity(updatedOrder.getQuantity());

        return modelMapper.map(orderRepository.save(entity), Order.class);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}