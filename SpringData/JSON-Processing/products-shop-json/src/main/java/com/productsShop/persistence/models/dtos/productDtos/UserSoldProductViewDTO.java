package com.productsShop.persistence.models.dtos.productDtos;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserSoldProductViewDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<SoldProductViewDTO> soldProducts;

    public UserSoldProductViewDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<SoldProductViewDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<SoldProductViewDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
