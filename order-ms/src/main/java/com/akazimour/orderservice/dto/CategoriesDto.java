package com.akazimour.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Embeddable
public class CategoriesDto implements Serializable {

    private String categoryName;
    private String consumerSegment;

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



}
