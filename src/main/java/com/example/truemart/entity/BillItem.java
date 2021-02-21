package com.example.truemart.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "product_and_size_id")
    private ProductsAndSizes pns;

    private BigDecimal price;

    private int quantities;

    @ManyToOne()
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
