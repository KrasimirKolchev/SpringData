package services;

import entities.Address;
import entities.Employee;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class AddressesService {
    private EntityManager entityManager;

    public AddressesService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String addressesWithEmployeeCount() {
        System.out.println("Please note that there can be some differences in addresses count related with the previous exercise!");
        List<String> addresses = this.entityManager
                .createQuery("select a from Address as a join a.employees e " +
                        "group by a.id order by count(e.id) desc", Address.class)
                .getResultList().stream().map(e -> String.format("%s, %s - %d employees"
                        , e.getText(), e.getTown().getName(), e.getEmployees().size()))
                .limit(10)
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        addresses.forEach(e -> sb.append(e).append("\n"));
        return sb.toString();
    }

    public void addAddressAndUpdateEmployee(String lName) {

        List<Employee> employees = this.entityManager
                .createQuery("select e from Employee as e where e.lastName = :lName", Employee.class)
                .setParameter("lName", lName).getResultList();

        Address address = addAddress();

        if (employees.size() != 0) {
            this.entityManager.getTransaction().begin();
            employees.forEach(e -> e.setAddress(address));
            this.entityManager.getTransaction().commit();
        } else {
            System.out.println("There is no entity (Employee) with last name: " + lName);
        }
    }

    private Address addAddress() {
        String newAddress = "Vitoshka 15";
        Address address = new Address();
        address.setText(newAddress);

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();

        return address;
    }
}
