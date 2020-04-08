package com.cardealerxml.services.salesService;

import com.cardealerxml.persistance.models.dtos.rootDtos.SalesDiscountRootDTO;
import com.cardealerxml.persistance.models.dtos.salesDtos.SalesDiscountsViewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaleService {

    void createSales();

    SalesDiscountRootDTO getSalesWithAppliedDiscount();
}
