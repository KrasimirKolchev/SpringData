package entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "teams")
public class Team {
    private int id;
    private String name;
    private Byte logo;
    private String initials;
    private Color primaryKitColor;
    private Color SecondaryKitColor;
    private Town town;
    private BigDecimal budget;

    public Team() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "logo")
    public Byte getLogo() {
        return logo;
    }

    public void setLogo(Byte logo) {
        this.logo = logo;
    }

    @Column(name = "initials", length = 3, nullable = false, unique = true)
    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    @ManyToOne
    @JoinColumn(name = "primary_kit_color", referencedColumnName = "id")
    public Color getPrimaryKitColor() {
        return primaryKitColor;
    }

    public void setPrimaryKitColor(Color primaryKitColor) {
        this.primaryKitColor = primaryKitColor;
    }

    @ManyToOne
    @JoinColumn(name = "secondary_kit_color")
    public Color getSecondaryKitColor() {
        return SecondaryKitColor;
    }

    public void setSecondaryKitColor(Color secondaryKitColor) {
        SecondaryKitColor = secondaryKitColor;
    }

    @ManyToOne(targetEntity = Town.class)
    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    @Column(name = "budget")
    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
