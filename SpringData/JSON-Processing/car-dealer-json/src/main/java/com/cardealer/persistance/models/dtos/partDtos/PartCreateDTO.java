package com.cardealer.persistance.models.dtos.partDtos;

import com.cardealer.persistance.models.entities.Supplier;
import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class PartCreateDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private int quantity;
    @Expose
    private Supplier supplier;


    public PartCreateDTO() {
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
