package com.cardealer.services.carsService;

import com.cardealer.common.ConstantMsg;
import com.cardealer.persistance.models.dtos.carDtos.CarPartsViewDto;
import com.cardealer.persistance.models.dtos.carDtos.CarCreateDTO;
import com.cardealer.persistance.models.dtos.carDtos.CarToyotaViewDTO;
import com.cardealer.persistance.models.entities.Car;
import com.cardealer.persistance.repositories.CarRepository;
import com.cardealer.services.partsService.PartService;
import com.cardealer.utils.fileUtil.FileUtil;
import com.cardealer.utils.validationUtil.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private Gson gson;
    private PartService partService;

    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, ModelMapper modelMapper,
                          FileUtil fileUtil, Gson gson, PartService partService) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.partService = partService;
    }

    @Override
    public void seedCars() throws IOException {
        if (this.carRepository.count() == 0) {
            CarCreateDTO[] carCreateDTOS = this.gson.fromJson(this.fileUtil
                    .readFileContent(ConstantMsg.CARS_DATA_PATH), CarCreateDTO[].class);

            Arrays.stream(carCreateDTOS).forEach(c -> {
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
    public List<CarToyotaViewDTO> getAllToyotaOrderByMakeAscTravelledDistanceDesc() {
        return this.carRepository.getAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream().map(c -> this.modelMapper.map(c, CarToyotaViewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarPartsViewDto> getAllCars() {
        return this.carRepository.findAll().stream()
                .map(c -> this.modelMapper.map(c, CarPartsViewDto.class))
                .collect(Collectors.toList());
    }
}


