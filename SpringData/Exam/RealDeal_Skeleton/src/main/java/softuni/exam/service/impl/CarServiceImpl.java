package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.CarSeedDTO;
import softuni.exam.models.dtos.CarViewDTO;
import softuni.exam.models.entities.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private static final DateTimeFormatter REGISTERED_ON_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final CarRepository carRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, Gson gson) {
        this.carRepository = carRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return this.fileUtil.readFile("src/main/resources/files/json/cars.json");
    }

    @Override
    public String importCars() throws IOException {
        CarSeedDTO[] cars = this.gson.fromJson(this.fileUtil
                .readFile("src/main/resources/files/json/cars.json"), CarSeedDTO[].class);
        StringBuilder sb = new StringBuilder();
        System.out.println();

        Arrays.stream(cars).forEach(c -> {

            if (this.validationUtil.isValid(c)) {
                Car car = this.modelMapper.map(c, Car.class);
                car.setRegisteredOn(LocalDate.parse(c.getRegisteredOn(), REGISTERED_ON_FORMAT));
                sb.append(String.format("Successfully imported car - %s - %s", car.getMake(), car.getModel()));
                this.carRepository.saveAndFlush(car);
            } else {
                sb.append("Invalid car");
            }
            sb.append(System.lineSeparator());
        });


        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {

        StringBuilder sb = new StringBuilder();

        this.carRepository
                .getCarsOrderByPicturesCountDescThenByMake()
                .stream()
                .map(c -> {
                    CarViewDTO carViewDTO = this.modelMapper.map(c, CarViewDTO.class);
                    carViewDTO.setSize(c.getPictures().size());
                    return carViewDTO;
                })
                .forEach(c -> sb
                .append(String.format(
                        "Car make - %s, model - %s\n\tKilometers - %s\n\tRegistered on - %s\n\tNumber of pictures - %d\n",
                        c.getMake(), c.getModel(), c.getKilometers(), c.getRegisteredOn(), c.getSize())));

        return sb.toString();
    }

    @Override
    public Car getCarById(Long id) {
        return this.carRepository.findById(id).orElse(null);
    }
}
