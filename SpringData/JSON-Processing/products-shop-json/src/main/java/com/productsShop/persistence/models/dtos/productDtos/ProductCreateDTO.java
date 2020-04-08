package com.productsShop.persistence.models.dtos.productDtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class ProductCreateDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductCreateDTO() {

    }

    @Length(min = 3, message = "Product name must be at least 3 symbols!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
