package com.example.truemart.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DoubleProduct {
    List<SingleProduct> slist;

    public DoubleProduct() {
        this.slist = new ArrayList<>();
    }
}
