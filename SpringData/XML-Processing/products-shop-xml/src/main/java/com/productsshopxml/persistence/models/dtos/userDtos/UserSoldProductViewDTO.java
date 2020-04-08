package com.productsshopxml.persistence.models.dtos.userDtos;

import com.productsshopxml.persistence.models.dtos.productDtos.ProductRootVIewDTO;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserSoldProductViewDTO {
    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlElement(name = "sold-products")
    private ProductRootVIewDTO soldProducts;

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

    public ProductRootVIewDTO getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductRootVIewDTO soldProducts) {
        this.soldProducts = soldProducts;
    }
}
