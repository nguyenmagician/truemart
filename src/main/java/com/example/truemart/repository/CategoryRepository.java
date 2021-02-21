package com.example.truemart.repository;

import com.example.truemart.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select ca.name from Category ca where ca.parent.name = :name")
    List<String> findChildrenOfCategoryName(@Param("name") String name);

    @Query("select ca.name from Category ca")
    List<String> findAllCategoriesName();
}
