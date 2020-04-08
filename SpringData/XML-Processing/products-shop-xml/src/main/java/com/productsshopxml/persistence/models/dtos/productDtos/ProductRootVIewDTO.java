package com.productsshopxml.persistence.models.dtos.productDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductRootVIewDTO {
    @XmlElement(name = "product")
    private List<ProductViewDTO> soldProducts;

    public ProductRootVIewDTO() {
    }

    public List<ProductViewDTO> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductViewDTO> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
