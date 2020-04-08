package com.productsshopxml.persistence.models.dtos.productDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsViewDTO {
    @XmlAttribute(name = "count")
    private int count;
    @XmlElement(name = "product")
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
