package com.example.truemart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class SizeInform {
    public String size;
    public boolean isStocked;
    public int stock;
    public BigDecimal price;
    public boolean isNew;

    public SizeInform(String size, boolean isStocked, int stock, BigDecimal price, boolean isNew) {        this.size = size;
        this.isStocked = isStocked;
        this.stock = stock;
        this.price = price;
        this.isNew = isNew;
    }
}
