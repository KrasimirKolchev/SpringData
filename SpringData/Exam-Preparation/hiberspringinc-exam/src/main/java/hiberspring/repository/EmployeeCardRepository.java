package hiberspring.repository;

import hiberspring.domain.entities.EmployeeCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeCardRepository extends JpaRepository<EmployeeCard, Long> {
    EmployeeCard getEmployeeCardByNumber(String number);
}
