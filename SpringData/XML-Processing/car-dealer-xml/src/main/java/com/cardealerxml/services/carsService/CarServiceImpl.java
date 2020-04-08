package com.cardealerxml.services.carsService;

import com.cardealerxml.common.ConstantMsg;
import com.cardealerxml.persistance.models.dtos.carDtos.*;
import com.cardealerxml.persistance.models.dtos.rootDtos.CarRootDTO;
import com.cardealerxml.persistance.models.dtos.rootDtos.CarToyotaRootDTO;
import com.cardealerxml.persistance.models.dtos.rootDtos.CarsPartsRootDTO;
import com.cardealerxml.persistance.models.entities.Car;
import com.cardealerxml.persistance.repositories.CarRepository;
import com.cardealerxml.services.partsService.PartService;
import com.cardealerxml.utils.validationUtil.ValidationUtil;
import com.cardealerxml.utils.xmlParser.XMLParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private XMLParser xmlParser;
    private PartService partService;

    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, ModelMapper modelMapper,
                          XMLParser xmlParser, PartService partService) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.partService = partService;
    }

    @Override
    public void seedCars() throws JAXBException, FileNotFoundException {
        if (this.carRepository.count() == 0) {
            CarRootDTO cars = this.xmlParser
                    .importFromXML(CarRootDTO.class, ConstantMsg.CARS_DATA_PATH);

            cars.getCars().forEach(c -> {
                if (this.validationUtil.isValid(c)) {
                    CarCreateDTO carCreateDTO = this.modelMapper.map(c, CarCreateDTO.class);
                    Car car = this.modelMapper.map(carCreateDTO, Car.class);
                    car.setParts(this.partService.getRandomParts());
                    this.carRepository.saveAndFlush(car);
                } else {
                    this.validationUtil.getViolationsData(c).stream()
                            .map(ConstraintViolation::getMessage)
                            .forEach(System.out::println);
                }
            });

        }
    }

    @Override
    public Car getRandomCar() {
        Random random = new Random();
        long index = random.nextInt((int) this.carRepository.count()) + 1;

        return this.carRepository.getOne(index);
    }

    @Override
    public long getCarsCount() {
        return this.carRepository.count();
    }

    @Override
    public CarToyotaRootDTO getAllToyotaOrderByMakeAscTravelledDistanceDesc() {
        CarToyotaRootDTO cars = new CarToyotaRootDTO();
        cars.setCars(this.carRepository.getAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream().map(c -> this.modelMapper.map(c, CarToyotaViewDTO.class))
                .collect(Collectors.toList()));

        return cars;
    }

    @Override
    public CarsPartsRootDTO getAllCars() {
        CarsPartsRootDTO cars = new CarsPartsRootDTO();
        cars.setCars(this.carRepository.findAll().stream()
                .map(c -> this.modelMapper.map(c, CarPartsViewDto.class))
                .collect(Collectors.toList()));
        return cars;
    }
}


