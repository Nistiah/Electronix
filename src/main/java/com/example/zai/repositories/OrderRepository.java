package com.example.zai.repositories;

import com.example.zai.enums.OrderStatus;
import com.example.zai.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByStatusAndUserId(OrderStatus shoppingCart, Long userId);

}
