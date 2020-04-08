package com.productsshopxml.persistence.models.dtos.categoryDtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryRootDTO {
    @XmlElement(name = "category")
    private List<CategorySeedDTO> categories;

    public CategoryRootDTO() {
    }

    public List<CategorySeedDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategorySeedDTO> categories) {
        this.categories = categories;
    }
}
