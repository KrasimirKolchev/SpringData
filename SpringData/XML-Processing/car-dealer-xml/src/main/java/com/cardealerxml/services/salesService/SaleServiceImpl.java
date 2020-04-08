package com.cardealerxml.services.salesService;

import com.cardealerxml.persistance.models.dtos.carDtos.CarViewDTO;
import com.cardealerxml.persistance.models.dtos.rootDtos.SalesDiscountRootDTO;
import com.cardealerxml.persistance.models.dtos.salesDtos.SalesDiscountsViewDto;
import com.cardealerxml.persistance.models.entities.*;
import com.cardealerxml.persistance.repositories.SaleRepository;
import com.cardealerxml.services.carsService.CarService;
import com.cardealerxml.services.customersService.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private SaleRepository saleRepository;
    private CarService carService;
    private CustomerService customerService;
    private ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarService carService, CustomerService customerService, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carService = carService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createSales() {
        for (long i = 0; i < 50; i++) {
            Sale sale = new Sale();
            sale.setCar(this.carService.getRandomCar());
            sale.setCustomer(this.customerService.getRandomCustomer());
            sale.setDiscount(getRandomDiscount());
            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public SalesDiscountRootDTO getSalesWithAppliedDiscount() {
        SalesDiscountRootDTO sales = new SalesDiscountRootDTO();
        sales.setSales(this.saleRepository.findAll().stream()
                .map(s -> {
                    SalesDiscountsViewDto sd = new SalesDiscountsViewDto();
                    sd.setCar(this.modelMapper.map(s.getCar(), CarViewDTO.class));
                    sd.setCustomerName(s.getCustomer().getName());
                    sd.setDiscount(s.getDiscount() / 100.0);

                    BigDecimal total = BigDecimal.ZERO;

                    BigDecimal totalWithDiscount = BigDecimal.ZERO;

                    for (Part p : s.getCar().getParts()) {
                        total = total.add(p.getPrice());
                        totalWithDiscount = totalWithDiscount.add(p.getPrice());
                    }
                    double discount = s.getDiscount() / 100.0;

                    totalWithDiscount = totalWithDiscount.subtract(totalWithDiscount.multiply(new BigDecimal(discount))
                            , MathContext.DECIMAL32);

                    sd.setPrice(total.setScale(2, RoundingMode.HALF_DOWN));
                    sd.setPriceWithDiscount(totalWithDiscount.setScale(2, RoundingMode.HALF_DOWN));
                    return sd;
                })
                .collect(Collectors.toList()));

        return sales;
    }

    private int getRandomDiscount() {
        List<Integer> discounts = new ArrayList<>();
        discounts.add(0);
        discounts.add(5);
        discounts.add(10);
        discounts.add(15);
        discounts.add(20);
        discounts.add(30);
        discounts.add(40);
        discounts.add(50);
        Random random = new Random();
        int index = random.nextInt(discounts.size());
        return discounts.get(index);
    }
}
