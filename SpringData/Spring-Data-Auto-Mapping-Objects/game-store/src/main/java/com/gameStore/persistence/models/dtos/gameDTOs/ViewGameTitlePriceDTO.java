package com.gameStore.persistence.models.dtos.gameDTOs;

import java.math.BigDecimal;

public class ViewGameTitlePriceDTO {
    private String title;
    private BigDecimal price;

    public ViewGameTitlePriceDTO() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
