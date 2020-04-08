package model.shampoos.implementations;

import model.labels.implementations.BasicLabel;
import model.enums.Size;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorColumn(name = "FN")
public class FreshNuke extends BasicShampoo {
    private static final String BRAND = "Fresh Nuke";
    private static final BigDecimal PRICE = new BigDecimal("9.33");
    private static final Size SIZE = Size.LARGE;

    public FreshNuke() {
    }

    public FreshNuke(BasicLabel classicLabel) {
        super(BRAND, PRICE, SIZE, classicLabel);
    }
}
