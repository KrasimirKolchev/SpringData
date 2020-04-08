package com.cardealerxml.persistance.models.dtos.carDtos;


import java.util.List;

public class CarsAndPartsViewDTO {

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
