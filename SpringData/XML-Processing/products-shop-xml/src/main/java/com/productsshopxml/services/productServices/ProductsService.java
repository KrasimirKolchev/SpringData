package com.productsshopxml.services.productServices;


import com.productsshopxml.persistence.models.dtos.productDtos.ProductsInRangeRootDTO;
import com.productsshopxml.persistence.models.entities.Product;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;

@Service
public interface ProductsService {
    void seedProducts() throws IOException, JAXBException;

    void register(Product product);

    ProductsInRangeRootDTO getProductsByPriceBetween(BigDecimal min, BigDecimal max);

}
