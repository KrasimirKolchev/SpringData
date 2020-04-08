package com.cardealerxml.services.supliersService;

import com.cardealerxml.persistance.models.dtos.rootDtos.LocalSuppliersRootDTO;
import com.cardealerxml.persistance.models.dtos.supplierDtos.LocalSupplierViewDTO;
import com.cardealerxml.persistance.models.entities.Supplier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public interface SupplierService {
    void seedSuppliersDate() throws IOException, JAXBException;

    Supplier getRandom();

    LocalSuppliersRootDTO getAllLocalSuppliers();
}
