package com.usersystem.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town {
    private long id;
    private String name;
    private Country country;
    private Set<User> bornIn;
    private Set<User> livingIn;

    public Town() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(targetEntity = Country.class)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @OneToMany(mappedBy = "bornTown")
    public Set<User> getBornIn() {
        return bornIn;
    }

    public void setBornIn(Set<User> bornIn) {
        this.bornIn = bornIn;
    }

    @OneToMany(mappedBy = "livingTown")
    public Set<User> getLivingIn() {
        return livingIn;
    }

    public void setLivingIn(Set<User> livingIn) {
        this.livingIn = livingIn;
    }
}
