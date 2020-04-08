package com.cardealer.persistance.models.dtos.carDtos;

import com.google.gson.annotations.Expose;

import java.util.List;

public class CarsAndPartsViewDTO {
    @Expose
    private List<CarPartsViewDto> carPartsViewDtos;

    public CarsAndPartsViewDTO() {
    }

    public List<CarPartsViewDto> getCarPartsViewDtos() {
        return carPartsViewDtos;
    }

    public void setCarPartsViewDtos(List<CarPartsViewDto> carPartsViewDtos) {
        this.carPartsViewDtos = carPartsViewDtos;
    }
}
