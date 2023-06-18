package com.example.zai.services;

import com.example.zai.dto.OrderDTO;
import com.example.zai.enums.OrderStatus;
import com.example.zai.mail.ClientWithJakarta;
import com.example.zai.mappers.OrderMapper;
import com.example.zai.models.Order;
import com.example.zai.models.OrderProduct;
import com.example.zai.models.User;
import com.example.zai.repositories.OrderProductRepository;
import com.example.zai.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final OrderProductRepository orderProductRepository;
    private final AuthenticationService authenticationService;
    void changeOrderStatus(Order order, OrderStatus orderStatus) {
        order.setStatus(orderStatus);
        orderRepository.save(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toDTO).toList();
    }

    public OrderDTO getShoppingCart() {
        deleteOrderProductsIfProductIsDeleted(orderRepository.findByStatusAndUserId(OrderStatus.SHOPPING_CART, authenticationService.getLoggedUser().getId()).orElseThrow());
        return orderMapper.toDTO(orderRepository.findByStatusAndUserId(OrderStatus.SHOPPING_CART, authenticationService.getLoggedUser().getId()).orElseThrow());
    }

    public OrderDTO getWishList() {
        deleteOrderProductsIfProductIsDeleted(orderRepository.findByStatusAndUserId(OrderStatus.WHISHLIST, authenticationService.getLoggedUser().getId()).orElseThrow());
        return orderMapper.toDTO(orderRepository.findByStatusAndUserId(OrderStatus.WHISHLIST, authenticationService.getLoggedUser().getId()).orElseThrow());
    }

    public int getNumberOfProductsInCart(User user) {
        try {
            deleteOrderProductsIfProductIsDeleted(orderRepository.findByStatusAndUserId(OrderStatus.SHOPPING_CART, user.getId()).get());
            return orderRepository.findByStatusAndUserId(OrderStatus.SHOPPING_CART, user.getId()).get().getOrderProductsList().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public int getNumberOfProductsInWishlist(User user) {
        try {
            deleteOrderProductsIfProductIsDeleted(orderRepository.findByStatusAndUserId(OrderStatus.WHISHLIST, user.getId()).get());
            return orderRepository.findByStatusAndUserId(OrderStatus.WHISHLIST, user.getId()).get().getOrderProductsList().size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void deleteOrderProductsIfProductIsDeleted(Order order) {
        List<OrderProduct> orderProducts = order.getOrderProductsList();
        for (OrderProduct orderProduct : orderProducts) {
            if (orderProduct.getProduct().getIsDeleted()) {
                orderProductRepository.delete(orderProduct);
            }

        }
    }

    public void order(Long id){
        Order order = orderRepository.findById(id).orElseThrow();
        order.setStatus(OrderStatus.PLACED);
        orderRepository.save(order);

        User user = order.getUser();

        Order cart = Order.builder()
                .user(user)
                .status(OrderStatus.SHOPPING_CART)
                .orderProductsList(new ArrayList<>())
                .build();

        orderRepository.save(cart);

        ClientWithJakarta mail = new ClientWithJakarta();
        mail.sendMail(orderMapper.toDTO(order), user.getEmail());

        System.out.println("Order placed");

    }



}
