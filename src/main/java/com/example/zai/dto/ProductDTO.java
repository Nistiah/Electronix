package com.example.zai.dto;

import com.example.zai.enums.Category;
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
public class ProductDTO {

    private Long id;
    @NotNull
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private Category category;

    private String image;

    private boolean isAvailable;


}
