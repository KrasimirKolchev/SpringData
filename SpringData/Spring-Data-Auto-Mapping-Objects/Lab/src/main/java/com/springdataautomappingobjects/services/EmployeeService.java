package com.springdataautomappingobjects.services;

import com.springdataautomappingobjects.entities.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface EmployeeService {
    void register(Employee employee);

    long count();

    Employee findById(long id);

    List<Employee> findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate date);
}
