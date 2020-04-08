package com.cardealer.services.carsService;

import com.cardealer.persistance.models.dtos.carDtos.CarPartsViewDto;
import com.cardealer.persistance.models.dtos.carDtos.CarToyotaViewDTO;
import com.cardealer.persistance.models.entities.Car;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface CarService {
    void seedCars() throws IOException;

    Car getRandomCar();

    long getCarsCount();

    List<CarToyotaViewDTO> getAllToyotaOrderByMakeAscTravelledDistanceDesc();

    List<CarPartsViewDto> getAllCars();
}
