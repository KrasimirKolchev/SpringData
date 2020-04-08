package advancedquerying.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "ingredients")
@NamedQueries({
        @NamedQuery(name = "Ingredient.deleteIngredientByName",
                query = "delete from advancedquerying.domain.entities.Ingredient as i where i.name = :name"),
        @NamedQuery(name = "Ingredient.increaseAllIngredientsPriceBy10PercentsNamedQuery",
                query = "update advancedquerying.domain.entities.Ingredient as i set i.price = i.price * 1.1"),
        @NamedQuery(name = "Ingredient.increaseIngredientsPriceBy10PercentsForIngredientsNamesNamedQuery",
                query = "update advancedquerying.domain.entities.Ingredient as i " +
                        "set i.price = i.price * 1.1 where i.name in :names")})


public class Ingredient extends BaseEntity {

    private String name;
    private BigDecimal price;
    private Set<Shampoo> shampoos;

    public Ingredient() {
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToMany(mappedBy = "ingredients",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public Set<Shampoo> getShampoos() {
        return this.shampoos;
    }

    public void setShampoos(Set<Shampoo> shampoos) {
        this.shampoos = shampoos;
    }
}
