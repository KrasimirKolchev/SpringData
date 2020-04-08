package model.ingredients.implementations;

import model.ingredients.ChemicalIngredient;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public abstract class BasicChemicalIngredient extends BasicIngredient implements ChemicalIngredient {

    @Column(name = "chemical_formula")
    protected String chemicalFormula;

    protected BasicChemicalIngredient() {
    }

    protected BasicChemicalIngredient(String name, BigDecimal price, String chemicalFormula) {
        super(name, price);
        this.setChemicalFormula(chemicalFormula);
    }

    @Override
    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    @Override
    public String getChemicalFormula() {
        return this.chemicalFormula;
    }
}
