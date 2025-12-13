package com.ecommerce.order_service.service;

import com.ecommerce.order_service.dto.OrderLineDtos;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderLineItems;
import com.ecommerce.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineDtos()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItems(orderLineItems);

        orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineDtos orderLineDtos) {

        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setId(orderLineDtos.getId());
        orderLineItems.setQuantity(orderLineDtos.getQuantity());
        orderLineItems.setPrice(orderLineDtos.getPrice());
        orderLineItems.setSkuCode(orderLineDtos.getSkuCode());
        return orderLineItems;
    }
}
