package com.cardealer.persistance.models.dtos.carDtos;

import com.google.gson.annotations.Expose;

public class CarViewDTO {
    @Expose
    private String Make;
    @Expose
    private String Model;
    @Expose
    private long TravelledDistance;

    public CarViewDTO() {
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public long getTravelledDistance() {
        return TravelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        TravelledDistance = travelledDistance;
    }
}
