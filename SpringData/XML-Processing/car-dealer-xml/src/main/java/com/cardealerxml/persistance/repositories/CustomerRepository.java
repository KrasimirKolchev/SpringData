package com.cardealerxml.persistance.repositories;

import com.cardealerxml.persistance.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> getAllByOrderByBirthDateAscYoungDriverAsc();

    @Query("select c from com.cardealerxml.persistance.models.entities.Customer c " +
            "where c.sales.size > 0 order by c.birthDate asc")
    List<Customer> getAllCustomersWithSales();
}
