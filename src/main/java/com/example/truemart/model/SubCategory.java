package com.example.truemart.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubCategory {
    private String name;
    private List<String> brands;

    public SubCategory() {
        this.brands = new ArrayList<>();
    }
}
