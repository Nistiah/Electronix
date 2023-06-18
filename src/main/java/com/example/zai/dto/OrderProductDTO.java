package com.example.zai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDTO {

    @NotNull
    private Long productId;

    @NotNull
    private String productName;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer quantity;

    private String image;

}
