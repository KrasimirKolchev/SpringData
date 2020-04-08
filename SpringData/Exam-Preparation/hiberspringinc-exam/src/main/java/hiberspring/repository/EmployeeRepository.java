package hiberspring.repository;

import hiberspring.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from hiberspring.domain.entities.Employee as e " +
            "join hiberspring.domain.entities.Product as p on e.branch.id = p.branch.id " +
            "order by concat(e.firstName, ' ', e.lastName), length(e.position) desc ")
    List<Employee> getEmployeesByBranchWhichHaveProducts();

    Employee findEmployeeByCardNumber(String number);
}
