package com.ecommerce.order_service.service;

import com.ecommerce.order_service.config.WebClientConfig;
import com.ecommerce.order_service.dto.InventoryResponse;
import com.ecommerce.order_service.dto.OrderLineDtos;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderLineItems;
import com.ecommerce.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineDtos()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(OrderLineItems::getSkuCode).toList();

        // Call Inventory service and check if stock is available if yes
        // then order some item.
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get()
                .uri("https://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponses != null;
        boolean isAllProductAvailInStock = Arrays.stream(inventoryResponses)
                        .allMatch(InventoryResponse::isInStock);

        if(isAllProductAvailInStock){
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("product is not in stock, please try again later");
        }

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
