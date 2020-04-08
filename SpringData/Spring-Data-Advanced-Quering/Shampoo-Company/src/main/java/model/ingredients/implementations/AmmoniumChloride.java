package model.ingredients.implementations;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue(value = "AM")
public class AmmoniumChloride extends BasicChemicalIngredient {
    private static final String NAME = "Ammonium Chloride";
    private static final BigDecimal PRICE = new BigDecimal("6.12");
    private static final String CHEMICAL_FORMULA = "NH4C1";

    public AmmoniumChloride() {
        super(NAME, PRICE, CHEMICAL_FORMULA);
    }

}
