package com.akazimour.catalogservice.service;

import com.akazimour.catalogservice.entity.Categories;
import com.akazimour.catalogservice.repository.CategoryRepository;
import com.akazimour.catalogservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InitDbService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

@Transactional
    public void deleteDb(){
        categoryRepository.deleteAll();
        productRepository.deleteAll();
    }
    @Transactional
    public void deleteAudTables() {
        jdbcTemplate.update("DELETE FROM categories_aud");
        jdbcTemplate.update("DELETE FROM product_aud");
        jdbcTemplate.update("DELETE FROM product_categories_aud");
    }
    @Transactional
    public void createCategories(){
        Categories categories = new Categories("Electronics", "Entertainment");
        Categories categories2 = new Categories("Hygiene-Home", "Home");
        Categories categories3 = new Categories("Home-Hobby", "Gardening");
        categoryRepository.save(categories);
        categoryRepository.save(categories2);
        categoryRepository.save(categories3);
    }


}
