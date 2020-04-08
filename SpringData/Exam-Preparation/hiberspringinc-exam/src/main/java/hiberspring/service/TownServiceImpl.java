package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.common.Constants;
import hiberspring.domain.dtos.TownViewDTO;
import hiberspring.domain.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {
    private final TownRepository townRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private Gson gson;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(Constants.PATH_TO_FILES + "towns.json"));
    }

    @Override
    public String importTowns(String townsFileContent) {
        TownViewDTO[] towns = this.gson.fromJson(townsFileContent, TownViewDTO[].class);
        StringBuilder sb = new StringBuilder();

        Arrays.stream(towns).forEach(t -> {
            if (this.validationUtil.isValid(t)) {
                sb.append(String.format("Successfully imported Town %s.", t.getName()));
                this.townRepository.saveAndFlush(this.modelMapper.map(t, Town.class));
            } else {
                sb.append("Error: Invalid data.");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public Town getTownByName(String name) {
        return this.townRepository.getTownByName(name);
    }
}
