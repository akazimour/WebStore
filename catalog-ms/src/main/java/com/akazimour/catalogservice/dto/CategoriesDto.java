package com.akazimour.catalogservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class CategoriesDto implements Serializable {

    private String categoryName;
    private String consumerSegment;
@JsonIgnore
    private List<ProductDto> products;

    public CategoriesDto() {
    }

    public CategoriesDto(String categoryName, String consumerSegment) {
        this.categoryName = categoryName;
        this.consumerSegment = consumerSegment;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getConsumerSegment() {
        return consumerSegment;
    }

    public void setConsumerSegment(String consumerSegment) {
        this.consumerSegment = consumerSegment;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriesDto that = (CategoriesDto) o;
        return Objects.equals(categoryName, that.categoryName) && Objects.equals(consumerSegment, that.consumerSegment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, consumerSegment);
    }
}
