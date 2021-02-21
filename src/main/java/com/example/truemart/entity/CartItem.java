package com.example.truemart.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "products_and_sizes_id")
    private ProductsAndSizes productsAndSizes;

    private int quantityPurchase;

    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
