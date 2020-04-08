package com.bookshopsystem.dto.book;

import com.bookshopsystem.enums.AgeRestriction;
import com.bookshopsystem.enums.EditionType;

import java.math.BigDecimal;

public class ReducedBook {

    private String title;
    private EditionType editionType;
    private AgeRestriction ageRestriction;
    private BigDecimal price;

    public ReducedBook(String title, EditionType editionType, AgeRestriction ageRestriction, BigDecimal price) {
        this.title = title;
        this.editionType = editionType;
        this.ageRestriction = ageRestriction;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s",
                this.title, this.editionType, this.ageRestriction, this.price);
    }
}
