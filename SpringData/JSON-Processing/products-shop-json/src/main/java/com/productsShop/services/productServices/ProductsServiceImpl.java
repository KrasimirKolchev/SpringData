package com.productsShop.services.productServices;

import com.google.gson.Gson;
import com.productsShop.common.ConstantMsg;
import com.productsShop.persistence.models.dtos.productDtos.ProductCreateDTO;
import com.productsShop.persistence.models.dtos.productDtos.ProductWithSellerDTO;
import com.productsShop.persistence.models.entities.Product;
import com.productsShop.persistence.repositories.ProductsRepository;
import com.productsShop.services.categoryServices.CategoryService;
import com.productsShop.services.userServices.UserService;
import com.productsShop.utils.fileUtil.FileUtil;
import com.productsShop.utils.validationUtil.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {
    private ProductsRepository productsRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private UserService userService;
    private CategoryService categoryService;
    private Gson gson;
    private FileUtil fileUtil;

    public ProductsServiceImpl(ProductsRepository productsRepository, ValidationUtil validationUtil,
                               ModelMapper modelMapper, UserService userService, CategoryService categoryService, Gson gson, FileUtil fileUtil) {
        this.productsRepository = productsRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.categoryService = categoryService;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public void register(Product product) {
        this.productsRepository.save(product);
    }

    @Override
    public void seedProducts() throws IOException {
        if (this.productsRepository.count() == 0) {
            ProductCreateDTO[] productCreateDTOS = this.gson
                    .fromJson(this.fileUtil
                                    .readFileContent(ConstantMsg.PRODUCTS_FILE_PATH), ProductCreateDTO[].class);

            Arrays.stream(productCreateDTOS).forEach(p -> {
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
    public List<ProductWithSellerDTO> getProductsByPriceBetween(BigDecimal min, BigDecimal max) {
        return this.productsRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(min, max)
                .stream()
                .map(p -> {
                    ProductWithSellerDTO productWithSellerDTO = this.modelMapper.map(p, ProductWithSellerDTO.class);
                    productWithSellerDTO.setFullName(
                            p.getSeller().getFirstName() + " " + p.getSeller().getLastName());
                    return productWithSellerDTO;
                })
                .collect(Collectors.toList());
    }
}
