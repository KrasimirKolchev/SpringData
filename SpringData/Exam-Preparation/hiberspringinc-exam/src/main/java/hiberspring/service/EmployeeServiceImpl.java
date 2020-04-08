package hiberspring.service;

import hiberspring.common.Constants;
import hiberspring.domain.dtos.EmployeesSeedRootDTO;
import hiberspring.domain.entities.Employee;
import hiberspring.repository.EmployeeRepository;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeCardService employeeCardService;
    private final BranchService branchService;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeCardService employeeCardService,
               BranchService branchService, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.employeeCardService = employeeCardService;
        this.branchService = branchService;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return Files.readString(Path.of(Constants.PATH_TO_FILES + "employees.xml"));
    }

    @Override
    public String importEmployees() throws JAXBException, FileNotFoundException {
        EmployeesSeedRootDTO employees = this.xmlParser
                .importFromXML(EmployeesSeedRootDTO.class, Constants.PATH_TO_FILES + "employees.xml");

        StringBuilder sb = new StringBuilder();

        employees.getEmployees().forEach(e -> {
            Employee employee = this.modelMapper.map(e, Employee.class);

            if (this.employeeRepository.findEmployeeByCardNumber(e.getCard()) == null) {
                employee.setCard(this.employeeCardService.getCardByNumber(e.getCard()));
                employee.setBranch(this.branchService.getBranchByName(e.getBranch()));

                if (this.validationUtil.isValid(employee)) {
                    sb.append(String.format("Successfully imported Employee %s %s."
                            , employee.getFirstName(), employee.getLastName()));
                    this.employeeRepository.saveAndFlush(employee);
                } else {
                    sb.append(Constants.INCORRECT_DATA_MESSAGE);
                }
            } else {
                sb.append(Constants.INCORRECT_DATA_MESSAGE);
            }

            sb.append(System.lineSeparator());
        });

        return sb.toString();
    }

    @Override
    public String exportProductiveEmployees() {
        List<Employee> employees = this.employeeRepository.getEmployeesByBranchWhichHaveProducts();

        StringBuilder sb = new StringBuilder();

        employees.forEach(e -> sb.append(String.format("Name: %s %s\nPosition: %s\nCard Number: %s\n"
                , e.getFirstName(), e.getLastName(), e.getPosition(), e.getCard().getNumber()
                )).append(Constants.OUTPUT_SEPARATOR));

        return sb.toString();
    }
}
