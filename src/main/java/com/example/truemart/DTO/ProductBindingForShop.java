package com.example.truemart.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBindingForShop {
    private long id;
    private String name;
    private String url1;
    private String url2;
    private String price;
    private boolean isDiscountable;
    private float discount;
    private String priceDiscount;
    private boolean isNew;
    private String shortDetail;
}
