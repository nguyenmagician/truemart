package com.example.truemart.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryFront {
    private String name;
    private String category_url;
    private String id_url;
    private FeatureProduct fproduct;
    private List<DoubleProduct> dlist;

    public CategoryFront(String name) {
        this.name = name;
        this.category_url = "/shop?category=" + name;
        this.dlist = new ArrayList<>();
        this.id_url = "#"+name;
    }
}
