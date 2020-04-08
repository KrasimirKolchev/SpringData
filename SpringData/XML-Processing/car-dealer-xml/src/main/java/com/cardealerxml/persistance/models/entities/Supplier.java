package com.cardealerxml.persistance.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "suppliers")
public class Supplier extends BaseEntity {
    private String name;
    private boolean isImporter;
    private List<Part> parts;

    public Supplier() {
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public boolean isIsImporter() {
        return isImporter;
    }

    public void setIsImporter(boolean isImporter) {
        this.isImporter = isImporter;
    }

    @OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER)
    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
