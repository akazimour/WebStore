package com.akazimour.catalogapi.dto;

import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ComponentScan
public class ProductDto {

    private Long id;
    private String productName;
    private Integer productPrice;
    private List<CategoriesDto> categories;


    public ProductDto() {
    }

    public ProductDto(Long id, String productName, Integer productPrice) {
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

    public List<CategoriesDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesDto> categories) {
        this.categories = categories;
    }
}
