package com.cardealerxml.services.customersService;

import com.cardealerxml.common.ConstantMsg;
import com.cardealerxml.persistance.models.dtos.customerDtos.*;
import com.cardealerxml.persistance.models.dtos.rootDtos.*;
import com.cardealerxml.persistance.models.entities.*;
import com.cardealerxml.persistance.repositories.CustomerRepository;
import com.cardealerxml.utils.validationUtil.ValidationUtil;
import com.cardealerxml.utils.xmlParser.XMLParser;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private XMLParser xmlParser;

    public CustomerServiceImpl(CustomerRepository customerRepository, ValidationUtil validationUtil,
                               ModelMapper modelMapper, XMLParser xmlParser) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCustomers() throws IOException, JAXBException {
        this.modelMapper.addMappings(new PropertyMap<CustomerCreateDTO, Customer>() {
            @Override
            protected void configure() {
                boolean isYoungDriver = false;

                if (source.isYoungDriver()) {
                    isYoungDriver = true;
                }
                map().setYoungDriver(isYoungDriver);
            }
        });

        if (this.customerRepository.count() == 0) {
            CustomerRootDTO customers = this.xmlParser
                    .importFromXML(CustomerRootDTO.class, ConstantMsg.CUSTOMERS_DATA_PATH);

            customers.getCustomers().forEach(c -> {
                if (this.validationUtil.isValid(c)) {
                    Customer customer = this.modelMapper.map(c, Customer.class);
                    customer.setBirthDate(LocalDateTime.parse(c.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    this.customerRepository.saveAndFlush(customer);
                } else {
                    this.validationUtil.getViolationsData(c).stream()
                            .map(ConstraintViolation::getMessage)
                            .forEach(System.out::println);
                }
            });
        }
    }

    @Override
    public Customer getRandomCustomer() {
        Random random = new Random();
        long index = random.nextInt((int) this.customerRepository.count()) + 1;

        return this.customerRepository.getOne(index);
    }

    @Override
    public CustomerOrderedRootDTO getAllCustomersOrderByBirthDateAscAndIsYoungDriver() {
        CustomerOrderedRootDTO customers = new CustomerOrderedRootDTO();
        customers.setCustomers(this.customerRepository.getAllByOrderByBirthDateAscYoungDriverAsc()
                .stream()
                .sorted((a, b) -> {
                    if (a.isYoungDriver() == !b.isYoungDriver()){
                        return 1;
                    }
                    if (!a.isYoungDriver() == b.isYoungDriver()){
                        return -1;
                    }
                    return 0;
                } )
                .map(c -> this.modelMapper.map(c, CustomerOrderedViewDTO.class))
                .collect(Collectors.toList()));
        return customers;
    }

    @Override
    public CustomerSalesRootDTO getAllCustomersWithSales() {

        List<CustomerSalesViewDTO> customerSalesViewDTOS = this.customerRepository
                .getAllCustomersWithSales()
                .stream().map(c -> {
                    CustomerSalesViewDTO cs = new CustomerSalesViewDTO();
                    cs.setFullName(c.getName());
                    cs.setBoughtCars(c.getSales().size());

                    BigDecimal total = BigDecimal.ZERO;

                    for (Sale s : c.getSales()) {
                        BigDecimal totalParts = BigDecimal.ZERO;

                        for (Part p : s.getCar().getParts()) {
                            totalParts = totalParts.add(p.getPrice());
                        }
                        double discount = s.getDiscount() / 100.0;

                        totalParts = totalParts.subtract(totalParts.multiply(new BigDecimal(discount))
                                , MathContext.DECIMAL32);

                        BigDecimal totalForCar = totalParts;

                        total = total.add(totalForCar);
                    }

                    //.setScale(2, RoundingMode.HALF_DOWN) -> to round the decimal to 2 digits after floating point
                    cs.setSpentMoney(total.setScale(2, RoundingMode.HALF_DOWN));
                    return cs;
                })
                .collect(Collectors.toList());

        CustomerSalesRootDTO customers = new CustomerSalesRootDTO();
        customers.setCustomers(customerSalesViewDTOS.stream()
                .sorted((a, b) -> {
                    int val = b.getSpentMoney().compareTo(a.getSpentMoney());

                    if (val == 0) {
                        val = Integer.compare(b.getBoughtCars(), a.getBoughtCars());
                    }

                    return val;
                })
                .collect(Collectors.toList()));

        return customers;
    }
}
