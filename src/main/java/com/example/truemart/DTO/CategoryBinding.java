package com.example.truemart.DTO;

import java.util.List;

public class CategoryBinding {
    private String nameCategory;
    private List<String> childs;

    public CategoryBinding() {
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public List<String> getChilds() {
        return childs;
    }

    public void setChilds(List<String> childs) {
        this.childs = childs;
    }
}
