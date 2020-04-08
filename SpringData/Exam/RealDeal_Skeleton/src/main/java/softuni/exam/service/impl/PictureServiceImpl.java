package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.PictureSeedDTO;
import softuni.exam.models.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final PictureRepository pictureRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final CarService carService;
    private final Gson gson;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, CarService carService, Gson gson) {
        this.pictureRepository = pictureRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.carService = carService;
        this.gson = gson;
    }


    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return this.fileUtil.readFile("src/main/resources/files/json/pictures.json");
    }

    @Override
    public String importPictures() throws IOException {
        PictureSeedDTO[] pictures = this.gson.fromJson(this.fileUtil
                .readFile("src/main/resources/files/json/pictures.json"), PictureSeedDTO[].class);

        StringBuilder sb = new StringBuilder();

        Arrays.stream(pictures).forEach(p -> {
           if (this.validationUtil.isValid(p)) {
               Picture picture = new Picture();
               if (this.pictureRepository.getPictureByName(p.getName()) == null) {
                   picture.setName(p.getName());
                   picture.setDateAndTime(LocalDateTime.parse(p.getDateAndTime(), DATE_TIME_FORMATTER));
                   picture.setCar(this.carService.getCarById(p.getCar()));

                   if (picture.getCar() != null) {
                       sb.append(String.format("Successfully imported picture - %s", picture.getName()));
                       this.pictureRepository.saveAndFlush(picture);
                   } else {
                       sb.append("Invalid picture");
                   }
               } else {
                   sb.append("Invalid picture");
               }
           } else {
               sb.append("Invalid picture");
           }
           sb.append(System.lineSeparator());
        });

        return sb.toString();
    }
}
