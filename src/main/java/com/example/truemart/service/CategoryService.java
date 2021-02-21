package com.example.truemart.service;

import com.example.truemart.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private  CategoryRepository categoryRepository;

    public List<String> getChildrenCategoriesOf(String name) {
        return categoryRepository.findChildrenOfCategoryName(name);
    }

    public List<String> getAllCategoriesName() {
        return  categoryRepository.findAllCategoriesName();
    }
}
