package alararestaurant.service;

import alararestaurant.domain.dtos.EmployeeSeedDTO;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final Gson gson;


    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository,
                               ValidationUtil validationUtil, ModelMapper modelMapper, FileUtil fileUtil, Gson gson) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile("src/main/resources/files/employees.json");
    }

    @Override
    public String importEmployees(String employees) {
        EmployeeSeedDTO[] employeesList = this.gson.fromJson(employees, EmployeeSeedDTO[].class);
        StringBuilder sb = new StringBuilder();

        Arrays.stream(employeesList).forEach(e -> {
            if (this.validationUtil.isValid(e)) {
                if (this.positionRepository.findPositionByName(e.getPosition()) == null) {
                    Position position = new Position();
                    position.setName(e.getPosition());
                    this.positionRepository.saveAndFlush(position);
                }
                Employee employee = this.modelMapper.map(e, Employee.class);
                employee.setPosition(this.positionRepository.findPositionByName(e.getPosition()));
                sb.append(String.format("Record %s successfully imported.", employee.getName()));
                this.employeeRepository.saveAndFlush(employee);
            } else {
                sb.append("Invalid data format.");
            }
            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public Employee findEmployeeByName(String name) {
        return this.employeeRepository.findEmployeeByName(name);
    }
}
