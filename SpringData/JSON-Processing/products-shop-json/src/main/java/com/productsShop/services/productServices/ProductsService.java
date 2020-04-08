package com.productsShop.services.productServices;

import com.productsShop.persistence.models.dtos.productDtos.ProductWithSellerDTO;
import com.productsShop.persistence.models.entities.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
public interface ProductsService {
    void seedProducts() throws IOException;

    void register(Product product);

    List<ProductWithSellerDTO> getProductsByPriceBetween(BigDecimal min, BigDecimal max);

}
