package com.example.zai.mappers;

import com.example.zai.dto.OrderDTO;
import com.example.zai.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderProductMapper orderProductMapper;

    public OrderDTO toDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .username(order.getUser().getUsername())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .orderDate(String.valueOf(order.getDate()))
                .totalPrice(order.getTotalPrice())
                .orderProducts(order.getOrderProductsList().stream().map(orderProductMapper::toDTO).toList())
                .status(order.getStatus())
                .build();
    }

 }

