package com.example.truemart.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoreFront {
    String category_url;
    List<CategoryFront> categoryFrontList;

    public StoreFront() {
        this.categoryFrontList = new ArrayList<>();
    }

    public StoreFront(String name) {
        this.categoryFrontList = new ArrayList<>();
        this.category_url = "/shop?category="+name;
    }
}
