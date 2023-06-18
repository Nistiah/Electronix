package com.example.zai.services;

import com.example.zai.dto.ProductDTO;
import com.example.zai.enums.Category;
import com.example.zai.enums.OrderStatus;
import com.example.zai.mappers.ProductMapper;
import com.example.zai.models.Order;
import com.example.zai.models.OrderProduct;
import com.example.zai.models.Product;
import com.example.zai.repositories.OrderProductRepository;
import com.example.zai.repositories.OrderRepository;
import com.example.zai.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final AuthenticationService authenticationService;

    private final OrderProductRepository orderProductRepository;


    public ProductDTO getProductById(Long id) {
        return ProductMapper.toDTO(productRepository.findById(id).orElseThrow());
    }

    public List<ProductDTO> getAllProductsWithoutPagination() {
        return productRepository.findAllByIsDeleted(false, Sort.by(Sort.Direction.ASC, "id")).stream().map(ProductMapper::toDTO).toList();
    }


    public Page<ProductDTO> getAllProductsByCategory(Category category, Pageable pageable) {
        Page<Product> productsPage = productRepository.findAllByCategoryAndIsDeleted(category, pageable, false);
        return productsPage.map(ProductMapper::toDTO);

//        return productRepository.findAllByCategoryAndIsDeleted(category, pageable,false).stream().map(ProductMapper::toDTO).toList();
    }

    public List<Category> getAllCategories() {
        return List.of(Category.values());
    }

    public Page<ProductDTO> getAllProductsByCategoryAndSearch(Category category, String search, Pageable pageable) {
        Page<Product> productsPage = productRepository.findAllByCategoryAndDescriptionContainingAndIsDeleted(category, search, pageable, false);
        return productsPage.map(ProductMapper::toDTO);
//        return productRepository.findAllByCategoryAndDescriptionContaining(category, search).stream().map(ProductMapper::toDTO).toList();
    }

    public Page<ProductDTO> getAllProductsBySearch(String search, Pageable pageable) {
        Page<Product> productsPage = productRepository.findAllByDescriptionContainingAndIsDeleted(search, pageable, false);
        return productsPage.map(ProductMapper::toDTO);
//        return productRepository.findAllByDescriptionContaining(search).stream().map(ProductMapper::toDTO).toList();
    }

    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAllByIsDeleted(false, pageable);
        return productsPage.map(ProductMapper::toDTO);
//        return productRepository.findAllByIsDeleted(false, Sort.by(Sort.Direction.ASC, "id")).stream().map(ProductMapper::toDTO).toList();
    }

    public void removeItemFromCart(Long id) {
        Long userId = authenticationService.getLoggedUser().getId();
        Order cart = orderRepository.findByStatusAndUserId(OrderStatus.SHOPPING_CART, userId).orElseThrow();
        cart.removeOrderProduct(cart.getOrderProductByProductId(id));
        orderRepository.save(cart);
    }

    public void addToCart(Long id) {
        Long userId = authenticationService.getLoggedUser().getId();
        Order cart = orderRepository.findByStatusAndUserId(OrderStatus.SHOPPING_CART, userId).orElseThrow();
        Product product = productRepository.findById(id).orElseThrow();
        if (cart.isProductInOrder(id)) {
            cart.getOrderProductByProductId(id).setQuantity(cart.getOrderProductByProductId(id).getQuantity() + 1);
            orderProductRepository.save(cart.getOrderProductByProductId(id));
            return;
        }
        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .quantity(1)
                .user(authenticationService.getLoggedUser())
                .build();
        orderProductRepository.save(orderProduct);
        cart.addOrderProduct(orderProduct);
        orderRepository.save(cart);
    }

    public void removeItemFromWishList(Long id) {
        Long userId = authenticationService.getLoggedUser().getId();
        Order whishList = orderRepository.findByStatusAndUserId(OrderStatus.WHISHLIST, userId).orElseThrow();
        whishList.removeOrderProduct(whishList.getOrderProductByProductId(id));
        orderRepository.save(whishList);
    }

    public void addToWishList(Long id) {
        Long userId = authenticationService.getLoggedUser().getId();
        Order wishList = orderRepository.findByStatusAndUserId(OrderStatus.WHISHLIST, userId).orElseThrow();
        Product product = productRepository.findById(id).orElseThrow();
        if (wishList.isProductInOrder(id)) {
            return;
        }
        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .quantity(1)
                .user(authenticationService.getLoggedUser())
                .build();
        orderProductRepository.save(orderProduct);
        wishList.addOrderProduct(orderProduct);
        orderRepository.save(wishList);
    }

    public void changeProduct(Long id, String name, String description, Category category, Double price, String image) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(BigDecimal.valueOf(price));
        product.setCategory(category);
        product.setImage(image);
        productRepository.save(product);
    }

    public List<String> getAllImages() {
        List<String> fileNames = new ArrayList<>();
        File folder = new File("src/main/resources/static/img");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        return fileNames;
    }

    public void addProduct(String name, String description, Category category, BigDecimal price, String image) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .category(category)
                .price(price)
                .image(image)
                .build();
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresent(product -> {
            product.setDeleted(true);
            productRepository.save(product);
        });
    }

}
