package com.cardealer.persistance.models.dtos.supplierDtos;

import com.google.gson.annotations.Expose;

public class SupplierCreateDTO {
    @Expose
    private String name;
    @Expose
    private boolean isImporter;

    public SupplierCreateDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return isImporter;
    }

    public void setImporter(boolean isImporter) {
        this.isImporter = isImporter;
    }
}
