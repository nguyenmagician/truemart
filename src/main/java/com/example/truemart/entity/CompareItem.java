package com.example.truemart.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CompareItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "products_and_sizes_id")
    private ProductsAndSizes productsAndSizes;

    @ManyToOne
    @JoinColumn(name = "wishlish_id")
    private Compare compare;
}
