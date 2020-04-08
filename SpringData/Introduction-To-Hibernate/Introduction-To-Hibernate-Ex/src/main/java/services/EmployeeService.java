package services;

import entities.Department;
import entities.Employee;
import entities.Project;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeService {
    private EntityManager entityManager;

    public EmployeeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public String employeesMaximumSalaries() {
        List<Department> departments = this.entityManager
                .createQuery("select d from Department as d join d.employees as e " +
                        "group by d.id having max(e.salary) not between 30000 and 50000", Department.class)
                .getResultList();

        return departments.stream().map(d -> {
            BigDecimal mSal = d.getEmployees().stream().map(Employee::getSalary)
                    .sorted(Comparator.reverseOrder())
                    .findFirst().orElse(BigDecimal.valueOf(0));
            return String.format("%s %s", d.getName(), mSal);
        }).collect(Collectors.joining("\r\n"));
    }

    public String findEmployeesByFirstName(String partName) {

        List<Employee> employees = this.entityManager
                .createQuery("select e from Employee as e " +
                        "where e.firstName like concat(:part, '%')", Employee.class)
                .setParameter("part", partName)
                .getResultList();

        StringBuilder sb = new StringBuilder();
        employees.forEach(e -> sb.append(String.format("%s %s - %s - ($%.2f)%n"
                , e.getFirstName(), e.getLastName(), e.getJobTitle(), e.getSalary())));
        return sb.toString();
    }

    public String getEmployeeWithProject(String input) {
        int id = Integer.parseInt(input);

        Employee employee = this.entityManager.find(Employee.class, id);
        List<Project> projects = new ArrayList<>(employee.getProjects());

        projects.sort(Comparator.comparing(Project::getName));

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle()));
        projects.forEach(p -> sb.append(String.format("\t%s%n", p.getName())));
        return sb.toString();
    }

    public String employeesFromDepartment() {
        String department = "Research and Development";
        List<Employee> employees = this.entityManager
                .createQuery("select e from Employee as e JOIN e.department d " +
                        "WHERE d.name = :department order by e.salary, e.id", Employee.class)
                .setParameter("department", department)
                .getResultList();

        StringBuilder sb = new StringBuilder();
        employees.forEach(e -> sb.append(String.format("\n%s %s from %s - $%s"
                , e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary())));
        return sb.toString();
    }

    public String employeesWithSalaryOver50k() {
        BigDecimal salary = new BigDecimal("50000");
        StringBuilder sb = new StringBuilder();
        List<Employee> resultList = this.entityManager
                .createQuery("select e from Employee as e where e.salary > :salary", Employee.class)
                .setParameter("salary", salary).getResultList();
        resultList.forEach(e -> sb.append(e.getFirstName()).append("\n"));
        return sb.toString();
    }

    public String containsEmployee(String fullName) {
        return this.entityManager
                .createQuery("select e from Employee as e" +
                        " where concat(e.firstName, ' ', e.lastName) = :fullName", Employee.class)
                .setParameter("fullName", fullName)
                .getResultList().isEmpty() ? "No" : "Yes";
    }

    public String increaseSalaries() {

        List<Employee> employees = this.entityManager
                .createQuery("select e from Employee as e join e.department as d where d.name = :d1 or d.name = :d2 or d.name = :d3 or d.name = :d4", Employee.class)
                .setParameter("d1", "Engineering")
                .setParameter("d2", "Tool Design")
                .setParameter("d3", "Marketing")
                .setParameter("d4", "Information Services")
                .getResultList();

        employees.forEach(e -> System.out.printf("%s %s ($%s)%n", e.getFirstName(), e.getLastName(), e.getSalary()));

        this.entityManager.getTransaction().begin();
        employees.forEach(e -> e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12))));
        this.entityManager.getTransaction().commit();

        StringBuilder sb = new StringBuilder();
        this.entityManager
                .createQuery("select e from Employee as e join e.department as d where d.name = :d1 or d.name = :d2 or d.name = :d3 or d.name = :d4", Employee.class)
                .setParameter("d1", "Engineering")
                .setParameter("d2", "Tool Design")
                .setParameter("d3", "Marketing")
                .setParameter("d4", "Information Services")
                .getResultList()
                .forEach(e -> sb.append(String.format("%s %s ($%.2f)%n",
                        e.getFirstName(), e.getLastName(), e.getSalary())));
        return sb.toString();
    }

}
