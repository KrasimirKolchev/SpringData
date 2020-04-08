package com.cardealerxml.services.customersService;

import com.cardealerxml.persistance.models.dtos.customerDtos.CustomerOrderedRootDTO;
import com.cardealerxml.persistance.models.dtos.rootDtos.CustomerSalesRootDTO;
import com.cardealerxml.persistance.models.entities.Customer;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public interface CustomerService {
    void seedCustomers() throws IOException, JAXBException;

    Customer getRandomCustomer();

    CustomerOrderedRootDTO getAllCustomersOrderByBirthDateAscAndIsYoungDriver();

    CustomerSalesRootDTO getAllCustomersWithSales();
}
