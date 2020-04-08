package com.productsShop.persistence.models.dtos.productDtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProductWithSellerDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private String fullName;

    public ProductWithSellerDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
