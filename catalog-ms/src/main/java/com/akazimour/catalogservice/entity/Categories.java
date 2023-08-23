package com.akazimour.catalogservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Audited
public class Categories implements Serializable {
    @Id
    private String categoryName;
    private String consumerSegment;
    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    public Categories() {
    }

    public Categories(String categoryName, String consumerSegment) {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProductToCategory(Product product){
        if (this.products==null) {
            this.products = new ArrayList<>();
            product.setCategories(null);
            this.products.add(product);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categories that = (Categories) o;
        return Objects.equals(categoryName, that.categoryName) && Objects.equals(consumerSegment, that.consumerSegment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryName, consumerSegment);
    }
}
