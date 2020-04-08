package com.productsShop.persistence.models.dtos.userDtos;

import com.google.gson.annotations.Expose;

public class UserWithProductViewDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private SoldProductsViewDTO soldProducts;

    public UserWithProductViewDTO() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SoldProductsViewDTO getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductsViewDTO soldProducts) {
        this.soldProducts = soldProducts;
    }
}
