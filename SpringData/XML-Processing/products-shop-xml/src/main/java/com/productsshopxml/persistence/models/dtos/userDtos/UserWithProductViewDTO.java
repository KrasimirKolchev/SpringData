package com.productsshopxml.persistence.models.dtos.userDtos;

import com.productsshopxml.persistence.models.dtos.productDtos.SoldProductsViewDTO;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithProductViewDTO {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute(name = "age")
    private int age;
    @XmlElement(name = "sold-products")
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
