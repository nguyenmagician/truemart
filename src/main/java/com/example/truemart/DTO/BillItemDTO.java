package com.example.truemart.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillItemDTO {
    private long pnsid;
    private int quantities;
    private String product_name;
    private int amount;
    private String price;
}
