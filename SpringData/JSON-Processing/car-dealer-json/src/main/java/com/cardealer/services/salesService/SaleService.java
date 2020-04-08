package com.cardealer.services.salesService;

import com.cardealer.persistance.models.dtos.salesDtos.SalesDiscountsViewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SaleService {

    void createSales();

    List<SalesDiscountsViewDto> getSalesWithAppliedDiscount();
}
