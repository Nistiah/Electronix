package com.example.zai.models;

import com.example.zai.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "products")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;


    @NotNull
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "category")
    private Category category;

    @Column(name = "image")
    private String image;

    private boolean isAvailable;


    @Length(max = 1000)
    @Column(name = "description")
    private String description;

    private boolean isDeleted = false;

    public Product(String name, String description, BigDecimal price, Category category, String image, boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.image = image;
        this.isAvailable = isAvailable;
    }


    public void setDeleted(boolean b) {
        this.isDeleted = b;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<OrderProduct> orderProducts;
}
