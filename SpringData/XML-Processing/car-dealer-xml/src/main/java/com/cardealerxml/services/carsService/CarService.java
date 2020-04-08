package com.cardealerxml.services.carsService;

import com.cardealerxml.persistance.models.dtos.rootDtos.CarToyotaRootDTO;
import com.cardealerxml.persistance.models.dtos.rootDtos.CarsPartsRootDTO;
import com.cardealerxml.persistance.models.entities.Car;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public interface CarService {
    void seedCars() throws IOException, JAXBException;

    Car getRandomCar();

    long getCarsCount();

    CarToyotaRootDTO getAllToyotaOrderByMakeAscTravelledDistanceDesc();

    CarsPartsRootDTO getAllCars();
}
