package com.productsshopxml.persistence.models.dtos.categoryDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriesByProductRootDTO {
    @XmlElement(name = "category")
    private List<CategoryByProductsViewDTO> categories;

    public CategoriesByProductRootDTO() {
    }

    public List<CategoryByProductsViewDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryByProductsViewDTO> categories) {
        this.categories = categories;
    }
}
