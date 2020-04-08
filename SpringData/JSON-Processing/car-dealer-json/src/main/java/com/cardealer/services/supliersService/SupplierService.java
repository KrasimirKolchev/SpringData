package com.cardealer.services.supliersService;

import com.cardealer.persistance.models.dtos.supplierDtos.LocalSupplierViewDTO;
import com.cardealer.persistance.models.entities.Supplier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface SupplierService {
    void seedSuppliersDate() throws IOException;

    Supplier getRandom();

    List<LocalSupplierViewDTO> getAllLocalSuppliers();
}
