package com.akazimour.orderservice.dto;


import java.util.List;

public class ProductSummaryDto {

    private Long id;
    private String productName;
    private Integer productPrice;
    private Integer quantity;

    private List<CategoriesDto> categories;
    public ProductSummaryDto() {
    }

    public ProductSummaryDto(Long id, String productName, Integer productPrice, Integer quantity) {
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<CategoriesDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesDto> categories) {
        this.categories = categories;
    }
}
