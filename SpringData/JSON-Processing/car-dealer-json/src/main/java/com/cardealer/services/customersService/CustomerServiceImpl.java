package com.cardealer.services.customersService;

import com.cardealer.common.ConstantMsg;
import com.cardealer.persistance.models.dtos.customerDtos.CustomerSalesViewDTO;
import com.cardealer.persistance.models.dtos.customerDtos.CustomerCreateDTO;
import com.cardealer.persistance.models.dtos.customerDtos.CustomerOrderedViewDTO;
import com.cardealer.persistance.models.entities.Customer;
import com.cardealer.persistance.models.entities.Part;
import com.cardealer.persistance.models.entities.Sale;
import com.cardealer.persistance.repositories.CustomerRepository;
import com.cardealer.utils.fileUtil.FileUtil;
import com.cardealer.utils.validationUtil.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerRepository customerRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private FileUtil fileUtil;
    private Gson gson;

    public CustomerServiceImpl(CustomerRepository customerRepository, ValidationUtil validationUtil,
                               ModelMapper modelMapper, FileUtil fileUtil, Gson gson) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public void seedCustomers() throws IOException {
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
            CustomerCreateDTO[] customerCreateDTOS = this.gson
                    .fromJson(fileUtil.readFileContent(ConstantMsg.CUSTOMERS_DATA_PATH), CustomerCreateDTO[].class);

            Arrays.stream(customerCreateDTOS).forEach(c -> {
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
    public List<CustomerOrderedViewDTO> getAllCustomersOrderByBirthDateAscAndIsYoungDriver() {
        return this.customerRepository.getAllByOrderByBirthDateAscYoungDriverAsc()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedViewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerSalesViewDTO> getAllCustomersWithSales() {

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

        return customerSalesViewDTOS.stream()
                .sorted((a, b) -> {
                    int val = b.getSpentMoney().compareTo(a.getSpentMoney());

                    if (val == 0) {
                        val = Integer.compare(b.getBoughtCars(), a.getBoughtCars());
                    }

                    return val;
                })
                .collect(Collectors.toList());
    }
}
