package com.example.truemart.repository;

import com.example.truemart.entity.Product;
import com.example.truemart.entity.ProductsAndCategories;
import com.example.truemart.entity.ProductsAndSizes;
import com.example.truemart.model.MaxMinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface ProductsAndSizesRepository extends JpaRepository<ProductsAndSizes,Long>,CustomizedProductsAndSizesRepository {


    @Query("select new com.example.truemart.entity.ProductsAndCategories(ps.product,ps.isStocked,ps.stock,ps.isNew,ps.price,cbp.category) " +
            "from ProductsAndSizes ps " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on ps.product = cbp.product " +
            "where cbp.category.name = :name or cbp.category.parent.name = :name"
            )
    List<ProductsAndCategories> findPCSBByCategoryName(@Param("name")String name);

    @Query("select p from ProductsAndSizes p where p.id = :id")
    Optional<ProductsAndSizes> findProductsAndSizesById(@Param("id")long id);

    @Query("select p from ProductsAndSizes p where p.product.id = :id and p.size.name = :size")
    Optional<ProductsAndSizes> findProductsAndSizesByIdAndSize(@Param("id")long id,@Param("size")String size);

    Optional<ProductsAndSizes> findFirstByProduct_IdOrderById(@Param("id")long id);

    @Query("select p.size.name from ProductsAndSizes p where p.product.id = :id")
    List<String> findSizesByProductId(@Param("id")long id);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.name in (:category)")
    List<ProductsAndCategories> findProductsAndSizesByCategory(@Param("category") List<String> category, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where  cbp.category.parent.name in (:category)")
    List<ProductsAndCategories> findProductsAndSizesByCategoryParent(@Param("category") List<String> category, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.name in (:category)  and cbp.brand.name in (:brand) ")
    List<ProductsAndCategories> findProductsAndSizesByCategory(@Param("category")List<String> category,@Param("brand")List<String> brand, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where  cbp.category.parent.name in (:category) and cbp.brand.name in (:brand) ")
    List<ProductsAndCategories> findProductsAndSizesByCategoryParent(@Param("category")List<String> category,@Param("brand")List<String> brand, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.name in (:category) and cbp.brand.name in (:brand) and pns.size.name in (:size) and (pns.price between :min and :max) ")
    List<ProductsAndCategories> findProductsAndSizesByCategory(@Param("category")List<String> category,@Param("brand")List<String> brand,@Param("size")List<String> size,@Param("min") BigDecimal min, @Param("max") BigDecimal max, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where   cbp.category.parent.name in (:category) and cbp.brand.name in (:brand) and pns.size.name in (:size) and (pns.price between :min and :max) ")
    List<ProductsAndCategories> findProductsAndSizesByCategoryParent(@Param("category")List<String> category,@Param("brand")List<String> brand,@Param("size")List<String> size,@Param("min") BigDecimal min, @Param("max") BigDecimal max, Pageable pageable);


//    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and pns.size.name in (:size) ")
//    List<ProductsAndCategories> findProductsAndSizesBy(@Param("category") List<String> category,@Param("size")List<String> size,Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and (pns.size.name in (:size)) and (pns.price between :min and :max)")
    List<ProductsAndCategories> findProductsAndSizesBy(@Param("category") List<String> category, @Param("size")List<String> size,@Param("min") BigDecimal min,@Param("max") BigDecimal max, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.name in (:category) ")
    Optional<Page<ProductsAndCategories>> findPagesOfProductsAndSizesByCategory(@Param("category") List<String> category, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.parent.name in (:category)")
    Optional<Page<ProductsAndCategories>> findPagesOfProductsAndSizesByCategoryParent(@Param("category") List<String> category, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.name in (:category)  and cbp.brand.name in (:brand) ")
    Optional<Page<ProductsAndCategories>> findPagesOfProductsAndSizesWithBrandByCategory(@Param("category")List<String> category,@Param("brand")List<String> brand, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.parent.name in (:category) and cbp.brand.name in (:brand) ")
    Optional<Page<ProductsAndCategories>> findPagesOfProductsAndSizesWithBrandByCategoryParent(@Param("category")List<String> category,@Param("brand")List<String> brand, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.name in (:category) and cbp.brand.name in (:brand) and pns.size.name in (:size) and (pns.price between :min and :max) ")
    Optional<Page<ProductsAndCategories>> findPagesOfProductsAndSizesWithBrandByCategory(@Param("category")List<String> category,@Param("brand")List<String> brand,@Param("size")List<String> size,@Param("min") BigDecimal min,@Param("max") BigDecimal max, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where   cbp.category.parent.name in (:category) and cbp.brand.name in (:brand) and pns.size.name in (:size) and (pns.price between :min and :max) ")
    Optional<Page<ProductsAndCategories>> findPagesOfProductsAndSizesWithBrandByCategoryParent(@Param("category")List<String> category,@Param("brand")List<String> brand,@Param("size")List<String> size,@Param("min") BigDecimal min,@Param("max") BigDecimal max, Pageable pageable);




//    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and pns.size.name in (:size) ")
//    Optional<Page<ProductsAndCategories>> findPagesOfProductsAndSizesBy(@Param("category") List<String> category,@Param("size")List<String> size,Pageable pageable);
//
//    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and (pns.size.name in (:size)) and (pns.price between :min and :max)")
//    Optional<Page<ProductsAndCategories>> findPagesOfProductsAndSizesBy(@Param("category") List<String> category, @Param("size")List<String> size,@Param("min") BigDecimal min,@Param("max") BigDecimal max, Pageable pageable);

    @Query("select distinct pns.size.name " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.name = :category ")
    List<String> findSizesByCategory(@Param("category")String category);

    @Query("select distinct pns.size.name " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.parent.name = :category")
    List<String> findSizesByCategoryParent(@Param("category")String category);


    @Query("select distinct c.brand.name from CategoriesAndBrandsAndProducts c where c.category.name = :category ")
    List<String> findBrandsByCategory(@Param("category")String category);

    @Query("select distinct c.brand.name from CategoriesAndBrandsAndProducts c where c.category.parent.name = :category ")
    List<String> findBrandsByCategoryParent(@Param("category")String category);

//    @Query("select  distinct pns.size.name " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and cbp.brand.name = :brand ")
//    Optional<List<String>> findSizesBy(@Param("category")List<String> category,@Param("brand")String brand);
//
//    @Query("select distinct pns.size.name " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and pns.size.name in (:size) ")
//    Optional<List<String>> findSizesBy(@Param("category") List<String> category,@Param("size")List<String> size);
//
//    @Query("select  distinct pns.size.name " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and (pns.size.name in (:size)) and (pns.price between :min and :max)")
//    Optional<List<String>> findSizesBy(@Param("category") List<String> category, @Param("size")List<String> size,@Param("min") BigDecimal min,@Param("max") BigDecimal max);



    @Query("select new com.example.truemart.model.MaxMinPrice(max(pns.price),min(pns.price)) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.name = :category")
    Optional<MaxMinPrice> findMaxMinByCategory(@Param("category")String category);

    @Query("select new com.example.truemart.model.MaxMinPrice(max(pns.price),min(pns.price)) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.parent.name = :category")
    Optional<MaxMinPrice> findMaxMinByCategoryParent(@Param("category")String category);

//    @Query("select new com.example.truemart.model.MaxMinPrice(max(pns.price),min(pns.price)) " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and cbp.brand.name = :brand ")
//    Optional<MaxMinPrice> findMaxMinBy(@Param("category")List<String> category,@Param("brand")String brand);
//
//    @Query("select new com.example.truemart.model.MaxMinPrice(max(pns.price),min(pns.price)) " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and cbp.brand.name = :brand and pns.size.name in (:size) ")
//    Optional<MaxMinPrice> findMaxMinBy(@Param("category")List<String> category,@Param("brand")String brand,@Param("size")List<String> size);
//
//    @Query("select new com.example.truemart.model.MaxMinPrice(max(pns.price),min(pns.price)) " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where (cbp.category.name in (:category) or cbp.category.parent.name in (:category)) and pns.size.name in (:size) ")
//    Optional<MaxMinPrice> findMaxMinBy(@Param("category") List<String> category,@Param("size")List<String> size);


    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where pns.product.id between 1 and 7 ")
    List<ProductsAndCategories> getTop7OfElectric();

//    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where cbp.category.name in (:category) and pns.product.name like %(:search)% ")
//    List<ProductsAndCategories> findProductsAndSizesWithSearchByCategory(@Param("category")List<String> category,List<String> search, Pageable pageable);
//
//    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
//            "from ProductsAndSizes pns " +
//            "join CategoriesAndBrandsAndProducts cbp " +
//            "on  pns.product = cbp.product " +
//            "where cbp.category.parent.name in (:category) and pns.product.name like %(:search)% ")
//    List<ProductsAndCategories> findProductsAndSizesWithSearchByCategoryParent(@Param("category")List<String> category,@Param("search")List<String> search, Pageable pageable);

    @Query("select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
            "from ProductsAndSizes pns " +
            "join CategoriesAndBrandsAndProducts cbp " +
            "on  pns.product = cbp.product " +
            "where cbp.category.parent.name in (:category) and pns.product.name like %:search%")
    List<ProductsAndCategories> findProductsAndSizesWithSearchByCategoryParent(@Param("category")List<String> category,@Param("search")String search, Pageable pageable);
}
