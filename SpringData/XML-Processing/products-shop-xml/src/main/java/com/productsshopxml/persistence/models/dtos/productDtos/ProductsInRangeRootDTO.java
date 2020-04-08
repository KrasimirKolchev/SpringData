package com.productsshopxml.persistence.models.dtos.productDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeRootDTO {
    @XmlElement(name = "product")
    private List<ProductWithSellerDTO> products;

    public ProductsInRangeRootDTO() {
    }

    public List<ProductWithSellerDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductWithSellerDTO> products) {
        this.products = products;
    }
}
