package softuni.workshop.service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.EmployeeRootDTO;
import softuni.workshop.data.dtos.EmployeeViewDTO;
import softuni.workshop.data.dtos.ProjectNameViewDTO;
import softuni.workshop.data.entities.Employee;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ProjectService projectService) {
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.projectService = projectService;
    }

    @Override
    public void importEmployees() throws JAXBException, FileNotFoundException {
        EmployeeRootDTO employees = this.xmlParser
                .importFromXML(EmployeeRootDTO.class, "src/main/resources/files/xmls/employees.xml");

        employees.getEmployees().forEach(e -> {
            Employee employee = this.modelMapper.map(e, Employee.class);
            employee.setProject(this.projectService.getProjectByName(e.getProject().getName()));

            this.employeeRepository.save(employee);
        });
    }

    @Override
    public boolean areImported() {
       return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile("src/main/resources/files/xmls/employees.xml");
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        List<EmployeeViewDTO> employees = this.employeeRepository
                .findEmployeesByAgeAfter(25)
                .stream()
                .map(e -> this.modelMapper.map(e, EmployeeViewDTO.class))
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        employees.forEach(e -> sb.append(String.format("Name: %s %s\n\tAge: %d\n\tProject Name: %s\n",
                e.getFirstName(), e.getLastName(), e.getAge(), e.getProject().getName())));

        return sb.toString();
    }
}
