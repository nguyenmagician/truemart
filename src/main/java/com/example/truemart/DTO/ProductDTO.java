package com.example.truemart.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private List<String> images;
    private String price;
    private boolean isDiscountable;
    private float discount;
    private String priceDiscount;
    private boolean isStock;
    private int sll;
    private String shortDetails;
    private String longDetails;
    private String size;
}
