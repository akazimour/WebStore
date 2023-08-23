package com.akazimour.catalogservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
public class Product implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String productName;
    private Integer productPrice;
    @ManyToMany
    private List<Categories> categories;


    public Product() {
    }

    public Product(Long id, String productName, Integer productPrice) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public void addCategoryToProduct(Categories category){
        if (this.categories==null) {
            this.categories = new ArrayList<>();
            category.setProducts(null);
            this.categories.add(category);

        }

    }
}
