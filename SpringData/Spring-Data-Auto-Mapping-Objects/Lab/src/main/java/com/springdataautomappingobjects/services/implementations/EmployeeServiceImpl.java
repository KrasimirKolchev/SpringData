package com.springdataautomappingobjects.services.implementations;

import com.springdataautomappingobjects.entities.Employee;
import com.springdataautomappingobjects.repositories.EmployeeRepository;
import com.springdataautomappingobjects.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void register(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public long count() {
        return this.employeeRepository.count();
    }

    @Override
    public Employee findById(long id) {
        return this.employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate date) {
        return this.employeeRepository.findAllByBirthdayBeforeOrderBySalaryDesc(date);
    }
}
