package com.akazimour.catalogservice.repository;
import com.akazimour.catalogservice.entity.Categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Categories,String> {
    public Optional<Categories> findByCategoryName ( @Param("categoryName") String categoryName);
}
