package com.springdataautomappingobjects.dto;

import com.springdataautomappingobjects.entities.Address;
import com.springdataautomappingobjects.entities.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeWithManagerDTO {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;
    private String managerLastName;

    public EmployeeWithManagerDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f – Manager: %s",
                this.firstName, this.lastName, this.salary,
                this.managerLastName == null ? "[no manager]" : this.managerLastName);
    }
}
