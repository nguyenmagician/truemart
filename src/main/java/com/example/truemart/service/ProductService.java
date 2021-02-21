package com.example.truemart.service;

import com.example.truemart.DTO.*;
import com.example.truemart.entity.*;
import com.example.truemart.model.MaxMinPrice;
import com.example.truemart.model.SubCategory;
import com.example.truemart.repository.CategoriesAndBrandsAndProductsRepository;
import com.example.truemart.repository.ProductRepository;
import com.example.truemart.repository.ProductsAndSizesRepository;
import com.example.truemart.tools.CurrencyTool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductsAndSizesRepository productsAndSizesRepository;

    private final CategoryService categoryService;

    private final CategoriesAndBrandsAndProductsRepository categoriesAndBrandsAndProductsRepository;

    public ProductService(ProductRepository productRepository, ProductsAndSizesRepository productsAndSizesRepository, CategoryService categoryService, CategoriesAndBrandsAndProductsRepository categoriesAndBrandsAndProductsRepository) {
        this.productRepository = productRepository;
        this.productsAndSizesRepository = productsAndSizesRepository;
        this.categoryService = categoryService;
        this.categoriesAndBrandsAndProductsRepository = categoriesAndBrandsAndProductsRepository;
    }

    public Optional<Product> getOptionalProductById(long id) {
        return productRepository.findProductById(id);
    }

    public StoreFront getStoreFront(String name) {
        StoreFront el = new StoreFront(name);

        List<String> mainList = new ArrayList<>();
        mainList.add(name);

        List<String> childrenOfElectronics = categoryService.getChildrenCategoriesOf(name);

        for (String str: childrenOfElectronics) {
            mainList.add(str);
        }

        for(String child : mainList) {
            CategoryFront cf = new CategoryFront(child);

            List<ProductsAndCategories> list = productsAndSizesRepository.findPCSBByCategoryName(child);

            if(list.size()!= 0) {
                // phan tu thu 0
                cf.setFproduct(convertFromProductsAndCategoriesAndSizesButFeatureProduct(list.get(0)));
                list.remove(0);
                // lay double product
                DoubleProduct d  = new DoubleProduct();
                int index = 0;

                for(int i = 0;i<list.size();i++) {
                    ProductsAndCategories p = list.get(i);
                    SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);

                    d.getSlist().add(s);
                    ++index;
                    if(index == 2 || i == list.size() - 1) {

                        index = 0;
                        cf.getDlist().add(d);

                        d = new DoubleProduct();
                    }

                    if(cf.getDlist().size() > 5) {
                        break;
                    }
                }

            }

            el.getCategoryFrontList().add(cf);

        }

        return el;
    }

    public SingleProduct convertFromProductsAndCategoriesAndSizes(ProductsAndCategories p) {
        SingleProduct sp = new SingleProduct(p.getProduct().getId());
        sp.setImg1_url(p.getProduct().getImages().get(0).getUrl());
        sp.setImg2_url(p.getProduct().getImages().get(1).getUrl());
        sp.setPrice(CurrencyTool.getPrice(p.getPrice()));
        sp.setPrice_after_discount(CurrencyTool.getDiscountPrice(p.getPrice(),p.getProduct().getDiscount()));
        sp.setDiscount(String.valueOf(p.getProduct().getDiscount()));
        sp.setDiscounted(p.getProduct().isDiscountable());
        sp.setNew(p.isNew());
        sp.setName(p.getProduct().getName());
        sp.setShort_detail(p.getProduct().getShort_detail());

        return sp;
    }

    public FeatureProduct convertFromProductsAndCategoriesAndSizesButFeatureProduct(ProductsAndCategories p) {
        FeatureProduct sp = new FeatureProduct(p.getProduct().getId());
        sp.setImg1_url(p.getProduct().getImages().get(0).getUrl());
        sp.setImg2_url(p.getProduct().getImages().get(1).getUrl());
        sp.setPrice(CurrencyTool.getPrice(p.getPrice()));
        sp.setPrice_after_discount(CurrencyTool.getDiscountPrice(p.getPrice(),p.getProduct().getDiscount()));
        sp.setDiscount(String.valueOf(p.getProduct().getDiscount()));
        sp.setDiscounted(p.getProduct().isDiscountable());
        sp.setNew(p.isNew());
        sp.setName(p.getProduct().getName());
        sp.setShort_detail(p.getProduct().getShort_detail());

        return sp;
    }

    public Optional<ProductsAndSizes> SingleProductProvider(long id) {
        return productsAndSizesRepository.findFirstByProduct_IdOrderById(id);
    }

    public Optional<ProductsAndSizes> SingleProductProvider(long id,String size) {
        return productsAndSizesRepository.findProductsAndSizesByIdAndSize(id,size);
    }

    public ProductsAndSizes findProductsAndSizesById(long id) {
        return productsAndSizesRepository.findProductsAndSizesById(id).get();
    }

    public ProductsAndSizes saveProductsAndSizes(ProductsAndSizes pns) {
      return productsAndSizesRepository.save(pns);
    }

    public ProductDTO convertProductsAndSizesToProductDTO(ProductsAndSizes pns) {
        ProductDTO dto = new ProductDTO();

        Product p = pns.getProduct();
        dto.setId(p.getId());
        dto.setName(p.getName());
        List<String> images = new ArrayList<>();
        for(Image image : p.getImages()) {
            images.add(image.getUrl());
        }
        dto.setImages(images);
        dto.setPrice(CurrencyTool.getPrice(pns.getPrice()));
        dto.setDiscountable(p.isDiscountable());
        dto.setDiscount(p.getDiscount());
        dto.setPriceDiscount(CurrencyTool.getDiscountPrice(pns.getPrice(),p.getDiscount()));
        dto.setStock(pns.isStocked());
        dto.setSll(pns.getStock());
        dto.setShortDetails(p.getShort_detail());
        dto.setLongDetails(p.getLong_detail());
        dto.setSize(pns.getSize().getName());

        return  dto;
    }

    public ProductDTO convertProductsAndSizesToProductDTO(ProductsAndSizes pns,String size) {
        ProductDTO dto = new ProductDTO();

        Product p = pns.getProduct();
        dto.setId(p.getId());
        dto.setName(p.getName());
        List<String> images = new ArrayList<>();
        for(Image image : p.getImages()) {
            images.add(image.getUrl());
        }
        dto.setPrice(CurrencyTool.getPrice(pns.getPrice()));
        dto.setDiscountable(p.isDiscountable());
        dto.setDiscount(p.getDiscount());
        dto.setPriceDiscount(CurrencyTool.getDiscountPrice(pns.getPrice(),p.getDiscount()));
        dto.setStock(pns.isStocked());
        dto.setSll(pns.getStock());
        dto.setShortDetails(p.getShort_detail());
        dto.setLongDetails(p.getLong_detail());
        dto.setSize(size);

        return  dto;
    }

    public List<String> getSizesOfProduct(long id) {
        return productsAndSizesRepository.findSizesByProductId(id);
    }

    public boolean checkSizeOfProduct(long id,String size){
        return productsAndSizesRepository.findProductsAndSizesByIdAndSize(id,size).isPresent();
    }

    public List<ProductsAndCategories> getProductsAndCategoriesBy(List<String> category,Pageable pageable) {
        String category_q = category.get(0).toLowerCase();

        if(category_q.equals("electronics")|| category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")) {
            return productsAndSizesRepository.findProductsAndSizesByCategoryParent(category,pageable);
        }
        return productsAndSizesRepository.findProductsAndSizesByCategory(category,pageable);

    }

    public List<ProductsAndCategories> getProductsAndCategoriesBy(List<String> category,List<String> brand,List<String> size, BigDecimal min, BigDecimal max,Pageable pageable) {

        String category_q = category.get(0).toLowerCase();

        if(category_q.equals("electronics")|| category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")) {
            return productsAndSizesRepository.findProductsAndSizesByCategoryParent(category,brand,size,min,max,pageable);
        }

        return productsAndSizesRepository.findProductsAndSizesByCategory(category,brand,size,min,max,pageable);

    }

    public List<ProductsAndCategories> getProductsAndCategoriesBy(List<String> category,List<String> brand,Pageable pageable) {

        String category_q = category.get(0).toLowerCase();

        if(category_q.equals("electronics")|| category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")) {
            return productsAndSizesRepository.findProductsAndSizesByCategoryParent(category,brand,pageable);
        }

        return productsAndSizesRepository.findProductsAndSizesByCategory(category,brand,pageable);

    }

//    public List<ProductsAndCategories> getProductsAndCategoriesBy(List<String> category, List<String> size,Pageable pageable)
//    { return productsAndSizesRepository.findProductsAndSizesBy(category,size,pageable);
//
//
//    }

    public List<ProductsAndCategories> getProductsAndCategoriesBy(List<String> category, List<String> size, BigDecimal min, BigDecimal max, Pageable pageable)
    { return productsAndSizesRepository.findProductsAndSizesBy(category,size,min,max,pageable);


    }

    public List<SingleProduct> getSingleProductsFrom(List<String> category,Pageable pageable) {
        List<SingleProduct> result = new ArrayList<>();

        List<ProductsAndCategories> list = getProductsAndCategoriesBy(category,pageable);

        if(list!= null) {
            for(ProductsAndCategories p : list) {
                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
                result.add(s);
            }
        }

        return  result;
    }

    public List<SingleProduct> getSingleProductsFrom(List<String> category,List<String> brand,Pageable pageable) {
        List<SingleProduct> result = new ArrayList<>();

        List<ProductsAndCategories> list = getProductsAndCategoriesBy(category,brand,pageable);

        if(list!= null) {
            for(ProductsAndCategories p : list) {
                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
                result.add(s);
            }
        }

        return  result;
    }

    public List<SingleProduct> getSingleProductsFrom(List<String> category,List<String> brand,List<String> size, BigDecimal min,BigDecimal max ,Pageable pageable) {
        List<SingleProduct> result = new ArrayList<>();

        List<ProductsAndCategories> list = getProductsAndCategoriesBy(category,brand,size,min,max,pageable);

        if(list!= null) {
            for(ProductsAndCategories p : list) {
                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
                result.add(s);
            }
        }

        return  result;
    }

//    public List<SingleProduct> getSingleProductsFrom(List<String> category,List<String> size,Pageable pageable) {
//        List<SingleProduct> result = new ArrayList<>();
//
//        List<ProductsAndCategories> list = getProductsAndCategoriesBy(category,size,pageable);
//
//        if(list!= null) {
//            for(ProductsAndCategories p : list) {
//                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
//                result.add(s);
//            }
//        }
//
//        return  result;
//    }

    public List<SingleProduct> getSingleProductsFrom(List<String> category,List<String> size,BigDecimal min,BigDecimal max,Pageable pageable) {
        List<SingleProduct> result = new ArrayList<>();

        List<ProductsAndCategories> list = getProductsAndCategoriesBy(category,size,min,max,pageable);

        if(list!= null) {
            for(ProductsAndCategories p : list) {
                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
                result.add(s);
            }
        }

        return  result;
    }

    public List<SubCategory> getSubCategories(List<String> category) {
        List<SubCategory> result = new ArrayList<>();
        List<String> itr = Arrays.asList("Camera","Tablet","Laptop","MobilePhone","Women","Sport","Living","Decor","Toys","Baby");




        for(String s : itr) {
            Optional<List<String>> brands = categoriesAndBrandsAndProductsRepository.findAllBrandByCategoryName(s);

            SubCategory sub = new SubCategory();
            sub.setName(s);
            sub.setBrands(brands.get());

            result.add(sub);
        }

        return  result;
    }

    public List<String> findAllCategoriesHaveCategoryParentName(String category) {
        return categoriesAndBrandsAndProductsRepository.findAllCategoriesHaveCategoryParentName(category).orElse(new ArrayList<>());
    }

    public List<String> findSizesBy(List<String> category) {
        String category_q = category.get(0).toLowerCase();
        if(category_q.equals("electronics")
                || category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")
        ){
            return productsAndSizesRepository.findSizesByCategoryParent(category_q);
        }

         return productsAndSizesRepository.findSizesByCategory(category_q);

    }

//    public List<String> findSizesBy(List<String> category,String brand) {
//        return productsAndSizesRepository.findSizesBy(category,brand).orElse(new ArrayList<>());
//    }
//
//    public List<String> findSizesBy(List<String> category,List<String> size) {
//        return productsAndSizesRepository.findSizesBy(category,size).orElse(new ArrayList<>());
//    }

//    public List<String> findSizesBy(List<String> category,List<String> size,BigDecimal min,BigDecimal max) {
//        return productsAndSizesRepository.findSizesBy(category,size,min,max).orElse(new ArrayList<>());
//    }

    public MaxMinPrice findMaxMinBy(List<String> category) {
        String category_q = category.get(0).toLowerCase();
        if(category_q.equals("electronics")
                || category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")
        ){
            return productsAndSizesRepository.findMaxMinByCategoryParent(category_q).orElse(new MaxMinPrice(BigDecimal.ZERO,BigDecimal.ZERO));
        }
        return productsAndSizesRepository.findMaxMinByCategory(category_q).orElse(new MaxMinPrice(BigDecimal.ZERO,BigDecimal.ZERO));
    }

//    public MaxMinPrice findMaxMinBy(List<String> category,String brand) {
//        return productsAndSizesRepository.findMaxMinBy(category,brand).orElse(new MaxMinPrice(BigDecimal.valueOf(10000000),BigDecimal.valueOf(0)));
//    }
//
//    public MaxMinPrice findMaxMinBy(List<String> category,String brand,List<String> size) {
//        return productsAndSizesRepository.findMaxMinBy(category,brand,size).orElse(new MaxMinPrice(BigDecimal.valueOf(10000000),BigDecimal.valueOf(0)));
//    }
//
//    public MaxMinPrice findMaxMinBy(List<String> category,List<String> size) {
//        return productsAndSizesRepository.findMaxMinBy(category,size).orElse(new MaxMinPrice(BigDecimal.valueOf(10000000),BigDecimal.valueOf(0)));
//    }

    public int getMaxPageOfSearch(List<String> category,List<String> search,Pageable pageable) {
        Page<ProductsAndCategories> page;

        String category_q = category.get(0).toLowerCase();
        if(category_q.equals("electronics")
                || category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")
        ) {
            page = productsAndSizesRepository.findPagesOfProductsAndSizesByCategoryParent(category,search,pageable);
        }else {
            page = productsAndSizesRepository.findPagesOfProductsAndSizesByCategory(category,search,pageable);
        }

        return  page.getTotalPages();

    }

    public int getMaxPage(List<String> category,Pageable pageable) {

        Optional<Page<ProductsAndCategories>> page;

        String category_q = category.get(0).toLowerCase();
        if(category_q.equals("electronics")
                || category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")
        ) {
            page = productsAndSizesRepository.findPagesOfProductsAndSizesByCategoryParent(category,pageable);
        }else {
            page = productsAndSizesRepository.findPagesOfProductsAndSizesByCategory(category,pageable);
        }

        if(page.isPresent()) {
            return  page.get().getTotalPages();
        }

        return 0;
    }



    public int getMaxPageHavingBrand(List<String> category,List<String> brand,Pageable pageable) {
        Optional<Page<ProductsAndCategories>> page;

        String category_q = category.get(0).toLowerCase();
        if(category_q.equals("electronics")
                || category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")
        ) {
            page = productsAndSizesRepository.findPagesOfProductsAndSizesWithBrandByCategoryParent(category,brand,pageable);
        } else {
            page = productsAndSizesRepository.findPagesOfProductsAndSizesWithBrandByCategory(category,brand,pageable);
        }


        if(page.isPresent()) {
            return  page.get().getTotalPages();
        }

        return 0;
    }


    public int getMaxPageHavingBrand(List<String> category,List<String> brand,List<String> size,BigDecimal min, BigDecimal max,Pageable pageable) {
        Optional<Page<ProductsAndCategories>> page ;


        String category_q = category.get(0).toLowerCase();
        if(category_q.equals("electronics")
                || category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")
        ) {
            page = productsAndSizesRepository.findPagesOfProductsAndSizesWithBrandByCategoryParent(category,brand,size,min,max,pageable);
        } else {
            page = productsAndSizesRepository.findPagesOfProductsAndSizesWithBrandByCategory(category,brand,size,min,max,pageable);
        }


        if(page.isPresent()) {
            return  page.get().getTotalPages();
        }

        return 0;
    }


//    public int getMaxPage(List<String> category,List<String> size,BigDecimal min, BigDecimal max,Pageable pageable) {
//        Optional<Page<ProductsAndCategories>> page = productsAndSizesRepository.findPagesOfProductsAndSizesBy(category,size,min, max,pageable);
//        if(page.isPresent()) {
//            return  page.get().getTotalPages();
//        }
//
//        return 0;
//    }

    public List<ProductsAndCategories> getTop7OfElectric(){
        return productsAndSizesRepository.getTop7OfElectric();
    }

    public List<SingleProduct> getHotDeals(){
        List<ProductsAndCategories> pns = getTop7OfElectric();
        List<SingleProduct> result = new ArrayList<>();

        for (ProductsAndCategories i : pns)
        {
            SingleProduct sp = convertFromProductsAndCategoriesAndSizes(i);
            result.add(sp);
        }

        return result;
    }

    public List<ProductsAndCategories> getProductsAndCategoriesWithSearchBy(List<String> category,List<String> search,Pageable pageable) {

        return productsAndSizesRepository.findProductsAndSizesWithSearchByCategory(category,search,pageable);

    }

    public List<ProductsAndCategories> getProductsAndCategoriesWithSearchByCategoryParent(List<String> category,List<String> search,Pageable pageable) {

        return productsAndSizesRepository.findProductsAndSizesWithSearchByCategoryParent(category,search,pageable);

    }


//    public List<ProductsAndCategories> getProductsAndCategoriesWithSearchBy(List<String> category,String search,List<String> size, BigDecimal min, BigDecimal max,Pageable pageable) {
//
//        return productsAndSizesRepository.findProductsAndSizesWithSearchBy(category,search,size,min,max,pageable);
//
//    }
//
//    public List<ProductsAndCategories> getProductsAndCategoriesWithSearchBy(String search,Pageable pageable) {
//
//        return productsAndSizesRepository.findProductsAndSizesWithSearchBy(search,pageable);
//
//    }
//
//
//    public List<ProductsAndCategories> getProductsAndCategoriesWithSearchBy(String search,List<String> size, BigDecimal min, BigDecimal max,Pageable pageable) {
//
//        return productsAndSizesRepository.findProductsAndSizesWithSearchBy(search,size,min,max,pageable);
//
//    }

    public List<SingleProduct> getSingleProductsWithSearchFrom(List<String> category,List<String> search,Pageable pageable) {
        List<SingleProduct> result = new ArrayList<>();

        List<ProductsAndCategories> list;
        String category_q = category.get(0).toLowerCase();
        if(category_q.equals("electronics") || category_q.equals("fashions") || category_q.equals("furnitures") || category_q.equals("kids")  )
        {
            list = getProductsAndCategoriesWithSearchByCategoryParent(category,search,pageable);
        }else {
            list = getProductsAndCategoriesWithSearchBy(category,search,pageable);
        }

        if(list!= null) {
            for(ProductsAndCategories p : list) {
                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
                result.add(s);
            }
        }

        return  result;
    }

//    public List<SingleProduct> getSingleProductsWithSearchFrom(List<String> category,String search,List<String> size, BigDecimal min,BigDecimal max ,Pageable pageable) {
//        List<SingleProduct> result = new ArrayList<>();
//
//        List<ProductsAndCategories> list = getProductsAndCategoriesWithSearchBy(category,search,size,min,max,pageable);
//
//        if(list!= null) {
//            for(ProductsAndCategories p : list) {
//                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
//                result.add(s);
//            }
//        }
//
//        return  result;
//    }
//
//    public List<SingleProduct> getSingleProductsWithSearchFrom(String search ,Pageable pageable) {
//        List<SingleProduct> result = new ArrayList<>();
//
//        List<ProductsAndCategories> list = getProductsAndCategoriesWithSearchBy(search,pageable);
//
//        if(list!= null) {
//            for(ProductsAndCategories p : list) {
//                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
//                result.add(s);
//            }
//        }
//
//        return  result;
//    }
//
//    public List<SingleProduct> getSingleProductsWithSearchFrom(String search,List<String> size, BigDecimal min,BigDecimal max ,Pageable pageable) {
//        List<SingleProduct> result = new ArrayList<>();
//
//        List<ProductsAndCategories> list = getProductsAndCategoriesWithSearchBy(search,size,min,max,pageable);
//
//        if(list!= null) {
//            for(ProductsAndCategories p : list) {
//                SingleProduct s = convertFromProductsAndCategoriesAndSizes(p);
//                result.add(s);
//            }
//        }
//
//        return  result;
//    }

    public List<String> getBrandsFromCategory(String category_q) {

        if(category_q.equals("electronics")
                || category_q.equals("fashions")
                || category_q.equals("furnitures")
                || category_q.equals("kids")
        ){
            return productsAndSizesRepository.findBrandsByCategoryParent(category_q);
        }

        return productsAndSizesRepository.findBrandsByCategory(category_q);
    }
}
