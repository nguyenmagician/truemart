package com.example.truemart.repository;

import com.example.truemart.entity.Brand;
import com.example.truemart.entity.CategoriesAndBrandsAndProducts;
import com.example.truemart.entity.Category;
import com.example.truemart.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriesAndBrandsAndProductsRepository extends JpaRepository<CategoriesAndBrandsAndProducts,Long> {
    @Query("select c.product from CategoriesAndBrandsAndProducts c where c.category = :category or c.category.parent = :category")
    Optional<List<Product>> findAllProductsByCategory(@Param("category") Category category);

    @Query("select c.product from CategoriesAndBrandsAndProducts c where c.category = :category or c.brand = :brand")
    Optional<List<Product>> findAllProductsByCategoryAndBrand(@Param("category") Category category,@Param("brand") Brand brand);

    @Query("select distinct c.brand.name from CategoriesAndBrandsAndProducts c where c.category.name = :category")
    Optional<List<String>> findAllBrandByCategoryName(@Param("category")String category);

    @Query("select c.category.name from CategoriesAndBrandsAndProducts c where c.category.parent.name = :category")
    Optional<List<String>>  findAllCategoriesHaveCategoryParentName(@Param("category")String category);
}
