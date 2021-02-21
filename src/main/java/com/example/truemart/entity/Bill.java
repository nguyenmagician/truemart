package com.example.truemart.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserTrueMart user;

    private Date created;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bill")
    private List<BillItem> billItems;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;

    private String notes;
    private String payment;
    private String coupon;

    private BigDecimal subPrice;
    private BigDecimal shippingPrice;
    private BigDecimal taxes;
    private BigDecimal totalPrice;

    public Bill() {
        this.billItems = new ArrayList<>();
    }

    public void addBillDetail(BillItem billItem) {
        billItem.setBill(this);
        this.billItems.add(billItem);
    }
}
