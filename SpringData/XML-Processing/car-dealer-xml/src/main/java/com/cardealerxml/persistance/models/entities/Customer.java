package com.cardealerxml.persistance.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private LocalDateTime birthDate;
    private boolean isYoungDriver;
    private List<Sale> sales;

    public Customer() {
        this.sales = new ArrayList<>();
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    @Column
    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean isYoungDriver) {
        this.isYoungDriver = isYoungDriver;
    }

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
