package core;

import services.AddressesService;
import services.EmployeeService;
import services.ProjectServices;
import services.TownService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppService {

    private TownService townService;
    private AddressesService addressesService;
    private EmployeeService employeeService;
    private ProjectServices projectServices;
    private BufferedReader reader;

    public AppService() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        this.townService = new TownService(entityManager);
        this.addressesService = new AddressesService(entityManager);
        this.employeeService = new EmployeeService(entityManager);
        this.projectServices = new ProjectServices(entityManager);
        this.reader = setReader();
    }

    private BufferedReader setReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    String execute(String command) throws IOException {

        String message = "";

        switch (command) {
            case "2":
                message += "\n===== Exercise 3 =====";
                townService.removeObjects();
                message += "\nCheck this exercise for changes manually.";
                break;
            case "3":
                message += "\n===== Exercise 3 =====(Auto tested with 'Svetlin Nakov' and 'John Doe')";
                String testName1 = "Svetlin Nakov";
                String testName2 = "John Doe";
                message += "\nAuto test 1 (Svetlin Nakov) --> " + employeeService.containsEmployee(testName1);
                message += "\nAuto test 2 (John Doe) --> " + employeeService.containsEmployee(testName2);
                System.out.println("Please enter manually name for the test : ");
                String manual = reader.readLine();
                message += "\nManual test --> " + employeeService.containsEmployee(manual);
                break;
            case "4":
                message += "\n===== Exercise 4 =====";
                message += "\n" + employeeService.employeesWithSalaryOver50k();
                break;
            case "5":
                message += "\n===== Exercise 5 =====";
                message += "\n" + employeeService.employeesFromDepartment();
                break;
            case "6":
                message += "\n===== Exercise 6 =====(Auto tested with Gilbert (employee with id 1))";
                String testLastName = "Gilbert";
                addressesService.addAddressAndUpdateEmployee(testLastName);
                message += "\nCheck this exercise for changes manually.";
                System.out.println("Enter last name: ");
                String manual1 = reader.readLine();
                addressesService.addAddressAndUpdateEmployee(manual1);
                break;
            case "7":
                message += "\n===== Exercise 7 =====";
                message += "\n" + addressesService.addressesWithEmployeeCount();
                break;
            case "8":
                message += "\n===== Exercise 8 =====(Auto tested with employee id 147 and 83)";
                String test1 = "147";
                String test2 = "83";
                message += "\nAuto test 1 (147) : " + employeeService.getEmployeeWithProject(test1);
                message += "\nAuto test 2 (83) : " + employeeService.getEmployeeWithProject(test2);

                System.out.println("Enter Employee id: ");
                String test3 = reader.readLine();
                message += "\n" + employeeService.getEmployeeWithProject(test3);
                break;
            case "9":
                message += "\n===== Exercise 9 =====";
                message += "\n" + projectServices.findLatestTenProjects();
                break;
            case "10":
                message += "\n===== Exercise 10 =====";
                message += "\n" + employeeService.increaseSalaries();
                break;
            case "11":
                message += "\n===== Exercise 11 =====";
                System.out.println("Enter Town name: ");
                String tName = "";
                message += "\n" + townService.removeTown(tName);
                break;
            case "12":
                message += "\n===== Exercise 12 =====";
                System.out.println("Enter first name starting with: ");
                String partName = reader.readLine().trim();
                message += "\n" + employeeService.findEmployeesByFirstName(partName);
                break;
            case "13":
                message += "\n===== Exercise 13 =====";
                message += "\n" + employeeService.employeesMaximumSalaries();
                break;
        }
        return message;
    }
}
