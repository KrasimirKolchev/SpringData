package com.cardealerxml.persistance.models.dtos.rootDtos;

import com.cardealerxml.persistance.models.dtos.carDtos.CarCreateDTO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarRootDTO {
    @XmlElement(name = "car")
    private List<CarCreateDTO> cars;

    public CarRootDTO() {
    }

    public List<CarCreateDTO> getCars() {
        return cars;
    }

    public void setCars(List<CarCreateDTO> cars) {
        this.cars = cars;
    }
}
