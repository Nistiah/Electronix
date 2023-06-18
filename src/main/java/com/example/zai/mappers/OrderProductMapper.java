package com.example.zai.mappers;

import com.example.zai.dto.OrderProductDTO;
import com.example.zai.models.OrderProduct;
import org.springframework.stereotype.Component;

@Component
public class OrderProductMapper {

    public OrderProductDTO toDTO(OrderProduct orderProduct) {
        return OrderProductDTO.builder()
                .productId(orderProduct.getProduct().getId())
                .productName(orderProduct.getProduct().getName())
                .quantity(orderProduct.getQuantity())
                .price(orderProduct.getProduct().getPrice())
                .image(orderProduct.getProduct().getImage())
                .build();
    }
}
