package com.akazimour.catalogservice.dto;

import java.util.Date;

public class ProductPriceElement {

    private Integer price;
    private Date date;

    public ProductPriceElement() {
    }

    public ProductPriceElement(Integer price, Date date) {
        this.price = price;
        this.date = date;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
