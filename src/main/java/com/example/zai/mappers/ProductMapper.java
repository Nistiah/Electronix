package com.example.zai.mappers;

import com.example.zai.dto.ProductDTO;
import com.example.zai.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .image(product.getImage())
                .isAvailable(product.isAvailable())
                .build();
    }


}
