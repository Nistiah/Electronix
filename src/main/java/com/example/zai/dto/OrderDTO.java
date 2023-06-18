package com.example.zai.dto;

import com.example.zai.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {


    private Long id;
    private Long userId;
    private String username;

    private String address;
    private String phoneNumber;

    private String orderDate;

    private double totalPrice;
    private List<OrderProductDTO> orderProducts;

    private OrderStatus status;






}
