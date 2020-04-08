package com.productsShop.persistence.models.dtos.userDtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class SoldProductsViewDTO {
    @Expose
    private int count;
    @Expose
    private List<ProductNamePriceViewDTO> products;

    public SoldProductsViewDTO() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProductNamePriceViewDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNamePriceViewDTO> products) {
        this.products = products;
    }
}
