package com.example.truemart.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class CategoriesAndBrandsAndProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public CategoriesAndBrandsAndProducts(Category category, Brand brand, Product product) {
        this.category = category;
        this.brand = brand;
        this.product = product;
    }
}
