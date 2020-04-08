package model.shampoos.implementations;

import model.enums.Size;
import model.labels.implementations.BasicLabel;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorColumn(name = "FI")
public class FiftyShades extends BasicShampoo {
    private static final String NAME = "Fifty Shades";
    private static final BigDecimal PRICE = new BigDecimal("6.69");
    private static final Size SIZE = Size.SMALL;

    public FiftyShades() {
    }

    public FiftyShades(BasicLabel classicLabel) {
        super(NAME, PRICE, SIZE, classicLabel);
    }

}
