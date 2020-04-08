package com.cardealer.services.customersService;

import com.cardealer.persistance.models.dtos.customerDtos.CustomerSalesViewDTO;
import com.cardealer.persistance.models.dtos.customerDtos.CustomerOrderedViewDTO;
import com.cardealer.persistance.models.entities.Customer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface CustomerService {
    void seedCustomers() throws IOException;

    Customer getRandomCustomer();

    List<CustomerOrderedViewDTO> getAllCustomersOrderByBirthDateAscAndIsYoungDriver();

    List<CustomerSalesViewDTO> getAllCustomersWithSales();
}
