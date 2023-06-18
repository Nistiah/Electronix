package com.example.zai.models;

import com.example.zai.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToMany
    @JoinColumn(name = "order_products_id")
    private List<OrderProduct> orderProductsList;

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProductsList.add(orderProduct);
    }

    public void removeOrderProduct(OrderProduct orderProduct) {
        orderProductsList.remove(orderProduct);
    }

    public OrderProduct
    getOrderProductByProductId(Long productId) {
        return orderProductsList.stream()
                .filter(orderProduct -> orderProduct.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order product not found"));
    }

    public boolean isProductInOrder(Long productId) {
        return orderProductsList.stream()
                .anyMatch(orderProduct -> orderProduct.getProduct().getId().equals(productId));
    }

//    @NotNull
    @Column(name = "status")
    private OrderStatus status;

//    @NotNull
    @Column(name = "total_price")
    private double totalPrice;

//    @NotNull
    @Column(name = "address")
    private String address;

//    @NotNull
    @Column(name = "phone_number")
    private String phoneNumber;

//    @NotNull
    @Column(name = "placed_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;


}
