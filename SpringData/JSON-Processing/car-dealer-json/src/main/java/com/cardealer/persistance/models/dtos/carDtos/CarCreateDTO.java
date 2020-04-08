package com.cardealer.persistance.models.dtos.carDtos;

import com.cardealer.persistance.models.entities.Sale;
import com.google.gson.annotations.Expose;

import java.util.List;

public class CarCreateDTO {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private long travelledDistance;
    @Expose
    private List<Sale> sales;

    public CarCreateDTO() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
