package com.order.order.controller;

import com.base.base.dto.OrderEventDTO;
import com.order.order.common.OrderResponse;
import com.order.order.dto.OrderDTO;
import com.order.order.kafka.OrderProducer;
import com.order.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderProducer orderProducer;

    @GetMapping("/getorders")
    public List<OrderDTO> getOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/order/{orderId}")
    public OrderDTO getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping("/addorder")
    public OrderResponse saveOrder(@RequestBody OrderDTO userDTO) {
        OrderEventDTO orderEventDTO = new OrderEventDTO("Order is commited","Pending");
        orderProducer.sendMessage(orderEventDTO);

        return orderService.saveOrder(userDTO);
    }

    @PutMapping("/updateorder")
    public OrderDTO updateOrder(@RequestBody OrderDTO userDTO) {
        return orderService.updateOrder(userDTO);
    }

    @DeleteMapping("/deleteorder/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId) {
        return orderService.deleteOrder(orderId);
    }
}
