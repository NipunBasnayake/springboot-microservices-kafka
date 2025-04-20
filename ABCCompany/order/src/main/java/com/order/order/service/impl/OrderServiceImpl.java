package com.order.order.service.impl;

import com.inventory.inventory.dto.InventoryDTO;
import com.order.order.common.ErrorOrderResponse;
import com.order.order.common.OrderResponse;
import com.order.order.common.SuccessOrderResponse;
import com.order.order.dto.OrderDTO;
import com.order.order.model.Orders;
import com.order.order.repo.OrderRepo;
import com.order.order.service.OrderService;
import com.product.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;

    private final WebClient inventoryWebClient;
    private final WebClient productWebClient;

    private static final String ORDER_NOT_FOUND_MESSAGE = "Order not found with id: ";

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Orders> orders = orderRepo.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .toList();
    }

    @Override
    public OrderResponse saveOrder(OrderDTO orderDTO) {
        int itemId = orderDTO.getItemId();
        try {
            InventoryDTO inventoryResponse = inventoryWebClient.get()
                    .uri("/api/v1/item/{itemId}", itemId)
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            assert inventoryResponse != null;

            int productId = inventoryResponse.getProductId();

            ProductDTO productResponse = productWebClient.get()
                    .uri("/api/v1/product/{productId}", productId)
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            assert productResponse != null;

            if (inventoryResponse.getQuantity() > 0) {
                if (productResponse.getForSale() == 1) {
                    Orders order = modelMapper.map(orderDTO, Orders.class);
                    Orders savedOrder = orderRepo.save(order);
                    return new SuccessOrderResponse(modelMapper.map(savedOrder, OrderDTO.class));
                } else {
                    return new ErrorOrderResponse("This item is not for sale");
                }
            } else {
                return new ErrorOrderResponse("Items not available");
            }
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().is5xxServerError()) {
                return new ErrorOrderResponse("Item not available in Database");
            }
            log.error("Error while searching inventory ", e);
        }
        return null;
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Optional<Orders> existingOrder = orderRepo.findById(orderDTO.getId());
        if (existingOrder.isEmpty()) {
            throw new NoSuchElementException(ORDER_NOT_FOUND_MESSAGE + orderDTO.getId());
        }

        Orders orderToUpdate = modelMapper.map(orderDTO, Orders.class);
        Orders updatedOrder = orderRepo.save(orderToUpdate);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
    public String deleteOrder(Integer orderId) {
        if (!orderRepo.existsById(orderId)) {
            throw new NoSuchElementException(ORDER_NOT_FOUND_MESSAGE + orderId);
        }

        orderRepo.deleteById(orderId);
        return "Order deleted successfully";
    }

    @Override
    public OrderDTO getOrderById(Integer orderId) {
        Optional<Orders> order = orderRepo.findById(orderId);
        return order.map(value -> modelMapper.map(value, OrderDTO.class))
                .orElseThrow(() -> new NoSuchElementException(ORDER_NOT_FOUND_MESSAGE + orderId));
    }
}