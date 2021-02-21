package com.example.truemart.repository;

import com.example.truemart.entity.ProductsAndCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomizedProductsAndSizesRepository {
    List<ProductsAndCategories> findProductsAndSizesWithSearchByCategoryParent(List<String> category,List<String> search, Pageable pageable);

    List<ProductsAndCategories> findProductsAndSizesWithSearchByCategory(List<String> category,List<String> search, Pageable pageable);

    Page<ProductsAndCategories> findPagesOfProductsAndSizesByCategory(List<String> category,List<String> search, Pageable pageable);

    Page<ProductsAndCategories> findPagesOfProductsAndSizesByCategoryParent(List<String> category,List<String> search, Pageable pageable);
}
