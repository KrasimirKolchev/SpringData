package com.springdataautomappingobjects.runners;

import com.springdataautomappingobjects.dto.EmployeeDTO;
import com.springdataautomappingobjects.dto.EmployeeWithManagerDTO;
import com.springdataautomappingobjects.dto.ManagerDTO;
import com.springdataautomappingobjects.entities.Address;
import com.springdataautomappingobjects.entities.Employee;
import com.springdataautomappingobjects.services.AddressService;
import com.springdataautomappingobjects.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;

@Controller
public class AppRunner implements CommandLineRunner {

    private EmployeeService employeeService;
    private AddressService addressService;

    @Autowired
    public AppRunner(EmployeeService employeeService, AddressService addressService) {
        this.employeeService = employeeService;
        this.addressService = addressService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (employeeService.count() == 0 && addressService.count() == 0) {
            seedDatabase();
            System.out.println("Seeded");
        }

        ModelMapper modelMapper = new ModelMapper();

        //1.
        Employee employee = employeeService.findById(1L);
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        System.out.println(employeeDTO);

        //2.
        Employee manager = employeeService.findById(2L);
        ManagerDTO managerDTO = modelMapper.map(manager, ManagerDTO.class);

        System.out.println(managerDTO);

        //3.1.
//        modelMapper.addMappings(new PropertyMap<Employee, EmployeeWithManagerDTO>() {
//            @Override
//            protected void configure() {
//                map().setManagerLastName(source.getManager().getLastName());
//            }
//        });
//
//        this.employeeService
//                .findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate.of(1990, 1, 1))
//                .stream()
//                .map(e -> modelMapper.map(e, EmployeeWithManagerDTO.class))
//                .forEach(System.out::println);

        System.out.println(" - - - - - - - - - - - - ");

        //3.2.
        TypeMap<Employee, EmployeeWithManagerDTO> typeMap =
                modelMapper.createTypeMap(Employee.class, EmployeeWithManagerDTO.class);
        typeMap.addMappings(map -> map.map(source -> source.getManager().getLastName(),
                EmployeeWithManagerDTO::setManagerLastName));

        this.employeeService
                .findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate.of(1990, 1, 1))
                .stream()
                .map(typeMap::map)
                .forEach(System.out::println);


    }

    private void seedDatabase() {
        Address address = new Address();
        address.setStreet("Street 1");
        address.setCity("City 1");
        address.setZipCode("123ASD45");
        address.setEmployees(new HashSet<>());

        Address address2 = new Address();
        address2.setStreet("Street 2");
        address2.setCity("City 2");
        address2.setZipCode("789ASD98");
        address2.setEmployees(new HashSet<>());

        Employee employee1 = new Employee();
        employee1.setFirstName("Georgi");
        employee1.setLastName("Georgiev");
        employee1.setAddress(address);
        employee1.setBirthday(LocalDate.of(1980, 6, 12));
        employee1.setSalary(BigDecimal.valueOf(1234.5));
        employee1.setOnHoliday(false);
        employee1.setEmployees(new HashSet<>());

        Employee employee2 = new Employee();
        employee2.setFirstName("Ivan");
        employee2.setLastName("Ivanov");
        employee2.setAddress(address);
        employee2.setBirthday(LocalDate.of(1981, 7, 1));
        employee2.setSalary(BigDecimal.valueOf(1334.77));
        employee2.setOnHoliday(false);
        employee2.setEmployees(new HashSet<>());

        Employee employee3 = new Employee();
        employee3.setFirstName("Peter");
        employee3.setLastName("Petrov");
        employee3.setAddress(address2);
        employee3.setBirthday(LocalDate.of(1990, 2, 28));
        employee3.setSalary(BigDecimal.valueOf(2345.25));
        employee3.setOnHoliday(true);
        employee3.setEmployees(new HashSet<>());

        Employee employee4 = new Employee();
        employee4.setFirstName("Petq");
        employee4.setLastName("Ivanova");
        employee4.setAddress(address);
        employee4.setBirthday(LocalDate.of(1989, 1, 30));
        employee4.setSalary(BigDecimal.valueOf(3456.75));
        employee4.setOnHoliday(false);
        employee4.setEmployees(new HashSet<>());


        //Manager
        employee2.getEmployees().add(employee1);
        employee2.getEmployees().add(employee3);
        employee2.getEmployees().add(employee4);

        //Set manager to employee
        employee1.setManager(employee2);
        employee3.setManager(employee2);
        employee4.setManager(employee2);

        address.getEmployees().add(employee1);
        address.getEmployees().add(employee2);
        address.getEmployees().add(employee4);
        address2.getEmployees().add(employee3);

        this.addressService.register(address);
        this.addressService.register(address2);

        this.employeeService.register(employee1);
        this.employeeService.register(employee2);
        this.employeeService.register(employee3);
        this.employeeService.register(employee4);
    }
}
