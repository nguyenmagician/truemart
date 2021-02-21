package com.example.truemart.repository;

import com.example.truemart.entity.ProductsAndCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class CustomizedProductsAndSizesRepositoryImpl implements CustomizedProductsAndSizesRepository{

    private final EntityManager entityManager;

    public CustomizedProductsAndSizesRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<ProductsAndCategories> findProductsAndSizesWithSearchByCategoryParent(List<String> category, List<String> search, Pageable pageable) {
        String sql = "select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
                "from ProductsAndSizes pns " +
                "join CategoriesAndBrandsAndProducts cbp " +
                "on  pns.product = cbp.product " +
                "where cbp.category.parent.name in (:category) and ";

        sql = sql.concat("( ");
        for(int i =0;i < search.size();i++) {
            String str = (i == 0) ?   "pns.product.name like '%"+search.get(i) +"%' " :   "or pns.product.name like '%"+search.get(i) +"%' ";
            sql = sql.concat(str);
        }
        sql = sql.concat(") ");
        System.out.println(sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter("category",category);
        query.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
        query.setMaxResults(1000);

        return query.getResultList();
    }

    @Override
    public List<ProductsAndCategories> findProductsAndSizesWithSearchByCategory(List<String> category, List<String> search, Pageable pageable) {
        String sql = "select new com.example.truemart.entity.ProductsAndCategories(pns.product,pns.isStocked,pns.stock,pns.isNew,pns.price,cbp.category) " +
                "from ProductsAndSizes pns " +
                "join CategoriesAndBrandsAndProducts cbp " +
                "on  pns.product = cbp.product " +
                "where cbp.category.name in (:category) and ";

        sql = sql.concat("( ");
        for(int i =0;i < search.size();i++) {
            String str = (i == 0) ?   "pns.product.name like '%"+search.get(i) +"%' " :   "or pns.product.name like '%"+search.get(i) +"%' ";
            sql = sql.concat(str);
        }
        sql = sql.concat(") ");
        System.out.println(sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter("category",category);
        query.setFirstResult(pageable.getPageSize() * pageable.getPageNumber());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }

    @Override
    public Page<ProductsAndCategories> findPagesOfProductsAndSizesByCategory(List<String> category, List<String> search, Pageable pageable) {
        String sql = "select  pns.product.name " +
                "from ProductsAndSizes pns " +
                "join CategoriesAndBrandsAndProducts cbp " +
                "on  pns.product = cbp.product " +
                "where cbp.category.name in (:category) and ";

        sql = sql.concat("( ");
        for(int i =0;i < search.size();i++) {
            String str = (i == 0) ?   "pns.product.name like '%"+search.get(i) +"%' " :   "or pns.product.name like '%"+search.get(i) +"%' ";
            sql = sql.concat(str);
        }
        sql = sql.concat(") ");
        System.out.println(sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter("category",category);

        System.out.println(query.getResultList().size());

        Page<ProductsAndCategories> rs = new PageImpl<>(query.getResultList(),pageable,query.getResultList().size());
        return rs;
    }

    @Override
    public Page<ProductsAndCategories> findPagesOfProductsAndSizesByCategoryParent(List<String> category, List<String> search, Pageable pageable) {
        String sql = "select   pns.product.name " +
                "from ProductsAndSizes pns " +
                "join CategoriesAndBrandsAndProducts cbp " +
                "on  pns.product = cbp.product " +
                "where cbp.category.parent.name in (:category) and ";

        sql = sql.concat("( ");
        for(int i =0;i < search.size();i++) {
            String str = (i == 0) ?   "pns.product.name like '%"+search.get(i) +"%' " :   "or pns.product.name like '%"+search.get(i) +"%' ";
            sql = sql.concat(str);
        }
        sql = sql.concat(") ");
        System.out.println(sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter("category",category);

        System.out.println(query.getResultList().size());

        Page<ProductsAndCategories> rs = new PageImpl<>(query.getResultList(),pageable,query.getResultList().size());


        return  rs;
    }
}
