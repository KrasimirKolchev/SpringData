package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.carDtos.CarPartsViewDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsPartsRootDTO {
    @XmlElement(name = "car")
    private List<CarPartsViewDto> cars;

    public CarsPartsRootDTO() {
    }

    public List<CarPartsViewDto> getCars() {
        return cars;
    }

    public void setCars(List<CarPartsViewDto> cars) {
        this.cars = cars;
    }
}
