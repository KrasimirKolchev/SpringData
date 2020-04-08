package com.productsshopxml.services.productServices;


import com.productsshopxml.common.ConstantMsg;
import com.productsshopxml.persistence.models.dtos.productDtos.ProductWithSellerDTO;
import com.productsshopxml.persistence.models.dtos.productDtos.ProductsInRangeRootDTO;
import com.productsshopxml.persistence.models.dtos.productDtos.ProductRootDTO;
import com.productsshopxml.persistence.models.entities.Product;
import com.productsshopxml.persistence.repositories.ProductsRepository;
import com.productsshopxml.services.categoryServices.CategoryService;
import com.productsshopxml.services.userServices.UserService;
import com.productsshopxml.utils.validationUtil.ValidationUtil;
import com.productsshopxml.utils.xmlParser.XMLParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private ProductsRepository productsRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private UserService userService;
    private CategoryService categoryService;
    private XMLParser xmlParser;

    public ProductsServiceImpl(ProductsRepository productsRepository, ValidationUtil validationUtil,
               ModelMapper modelMapper, UserService userService, CategoryService categoryService, XMLParser xmlParser) {
        this.productsRepository = productsRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void register(Product product) {
        this.productsRepository.save(product);
    }

    @Override
    public void seedProducts() throws IOException, JAXBException {
        if (this.productsRepository.count() == 0) {
            ProductRootDTO products = this.xmlParser.importFromXML(ProductRootDTO.class, ConstantMsg.PRODUCTS_FILE_PATH);

            products.getProducts().forEach(p -> {
                if (this.validationUtil.isValid(p)) {
                    Product product = this.modelMapper.map(p, Product.class);
                    product.setSeller(this.userService.getRandomUser());
                    Random random = new Random();
                    int num = random.nextInt(2);

                    if (num == 1) {
                        product.setBuyer(this.userService.getRandomUser());
                    }
                    product.setCategories(this.categoryService.getRandomCategories());

                    this.productsRepository.saveAndFlush(product);
                } else {
                    this.validationUtil.getViolationsData(p)
                            .stream().map(ConstraintViolation::getMessage)
                            .forEach(System.out::println);
                }
            });

        }
    }

    @Override
    public ProductsInRangeRootDTO getProductsByPriceBetween(BigDecimal min, BigDecimal max) {
        ProductsInRangeRootDTO products = new ProductsInRangeRootDTO();
        products.setProducts(this.productsRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(min, max)
                .stream()
                .map(p -> {
                    ProductWithSellerDTO productWithSellerDTO = this.modelMapper.map(p, ProductWithSellerDTO.class);
                    productWithSellerDTO.setSellerFullName(
                            p.getSeller().getFirstName() + " " + p.getSeller().getLastName());
                    return productWithSellerDTO;
                })
                .collect(Collectors.toList()));

        return products;

    }
}
