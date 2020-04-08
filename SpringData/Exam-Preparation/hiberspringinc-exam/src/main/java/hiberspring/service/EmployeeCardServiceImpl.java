package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.common.Constants;
import hiberspring.domain.dtos.EmployeeCardViewDTO;
import hiberspring.domain.dtos.TownViewDTO;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.domain.entities.Town;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {
    private final EmployeeCardRepository employeeCardRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private Gson gson;

    @Autowired
    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, ValidationUtil validationUtil,
                                   ModelMapper modelMapper, Gson gson) {
        this.employeeCardRepository = employeeCardRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public Boolean employeeCardsAreImported() {
        return this.employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(Constants.PATH_TO_FILES + "employee-cards.json"));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) {
        EmployeeCardViewDTO[] cards = this.gson.fromJson(employeeCardsFileContent, EmployeeCardViewDTO[].class);
        StringBuilder sb = new StringBuilder();

        Arrays.stream(cards).forEach(c -> {
            if (this.validationUtil.isValid(c)) {
                if (!this.contains(c.getNumber())) {
                    sb.append(String.format("Successfully imported Employee Card %s.", c.getNumber()));
                    this.employeeCardRepository.saveAndFlush(this.modelMapper.map(c, EmployeeCard.class));
                } else {
                    sb.append("Error: Invalid data.");
                }
            } else {
                sb.append("Error: Invalid data.");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    private boolean contains(String number) {
        EmployeeCard card = this.employeeCardRepository.getEmployeeCardByNumber(number);

        return card != null;
    }

    @Override
    public EmployeeCard getCardByNumber(String number) {
        return this.employeeCardRepository.getEmployeeCardByNumber(number);
    }
}
