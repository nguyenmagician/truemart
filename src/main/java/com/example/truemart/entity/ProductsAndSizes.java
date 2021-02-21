package com.example.truemart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ProductsAndSizes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private boolean isStocked;

    private int stock;

    private boolean isNew;

    private BigDecimal price;

    private Date updated;
}
