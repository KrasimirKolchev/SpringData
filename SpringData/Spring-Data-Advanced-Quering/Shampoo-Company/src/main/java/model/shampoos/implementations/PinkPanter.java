package model.shampoos.implementations;

import model.enums.Size;
import model.labels.implementations.BasicLabel;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorColumn(name = "PI")
public class PinkPanter extends BasicShampoo {
    private static final String BRAND = "Pink Panther";
    private static final BigDecimal PRICE = new BigDecimal("8.50");
    private static final Size SIZE = Size.MEDIUM;

    public PinkPanter() {
    }

    public PinkPanter(BasicLabel classicLabel) {
        super(BRAND, PRICE, SIZE, classicLabel);
    }
}
