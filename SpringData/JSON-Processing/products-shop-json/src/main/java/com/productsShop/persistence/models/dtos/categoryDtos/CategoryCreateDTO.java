package com.productsShop.persistence.models.dtos.categoryDtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

public class CategoryCreateDTO {
    @Expose
    private String name;

    public CategoryCreateDTO(String name) {
        this.name = name;
    }

    @Length(min = 3, max = 15, message = "Category name must be between 3 and 15 symbols!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
