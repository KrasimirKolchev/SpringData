package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.carDtos.CarToyotaViewDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarToyotaRootDTO {
    @XmlElement(name = "car")
    private List<CarToyotaViewDTO> cars;

    public CarToyotaRootDTO() {
    }

    public List<CarToyotaViewDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarToyotaViewDTO> cars) {
        this.cars = cars;
    }
}
