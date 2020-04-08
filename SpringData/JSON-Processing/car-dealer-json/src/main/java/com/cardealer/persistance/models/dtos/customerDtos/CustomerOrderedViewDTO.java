package com.cardealer.persistance.models.dtos.customerDtos;

import com.cardealer.persistance.models.entities.Sale;
import com.google.gson.annotations.Expose;

import java.util.List;

public class CustomerOrderedViewDTO {
    @Expose
    private long Id;
    @Expose
    private String Name;
    @Expose
    private String BirthDate;
    @Expose
    private boolean IsYoungDriver;
    @Expose
    private List<Sale> Sales;

    public CustomerOrderedViewDTO() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        this.BirthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return IsYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        IsYoungDriver = youngDriver;
    }

    public List<Sale> getSales() {
        return Sales;
    }

    public void setSales(List<Sale> sales) {
        this.Sales = sales;
    }
}
