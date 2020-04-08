package com.cardealer.persistance.models.dtos.carDtos;

import com.cardealer.persistance.models.dtos.partDtos.PartViewDTO;
import com.google.gson.annotations.Expose;

import java.util.List;

public class CarPartsViewDto {
    @Expose
    private CarViewDTO car;
    @Expose
    private List<PartViewDTO> parts;

    public CarPartsViewDto() {
    }

    public CarViewDTO getCar() {
        return car;
    }

    public void setCar(CarViewDTO car) {
        this.car = car;
    }

    public List<PartViewDTO> getParts() {
        return parts;
    }

    public void setParts(List<PartViewDTO> parts) {
        this.parts = parts;
    }
}
