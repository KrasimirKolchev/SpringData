package com.productsShop.controller;

import com.google.gson.Gson;
import com.productsShop.common.ConstantMsg;
import com.productsShop.persistence.models.dtos.UsersAndProductsViewDTO;
import com.productsShop.persistence.models.dtos.categoryDtos.CategoryByProductsViewDTO;
import com.productsShop.persistence.models.dtos.productDtos.ProductWithSellerDTO;
import com.productsShop.persistence.models.dtos.productDtos.UserSoldProductViewDTO;
import com.productsShop.persistence.models.dtos.userDtos.UserWithProductViewDTO;
import com.productsShop.services.categoryServices.CategoryService;
import com.productsShop.services.productServices.ProductsService;
import com.productsShop.services.userServices.UserService;
import com.productsShop.utils.fileUtil.FileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class AppController implements CommandLineRunner {
    private UserService userService;
    private ProductsService productsService;
    private CategoryService categoryService;
    private Gson gson;
    private FileUtil fileUtil;
    private ModelMapper modelMapper;

    @Autowired
    public AppController(UserService userService, ProductsService productsService, CategoryService categoryService, Gson gson, FileUtil fileUtil, ModelMapper modelMapper) {
        this.userService = userService;
        this.productsService = productsService;
        this.categoryService = categoryService;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        this.categoryService.seedCategories();
        this.userService.seedUsers();
        this.productsService.seedProducts();

        //резултатите са в resources/results

        writeProductsWithoutSellerInRange();

        writeUsersWithSoldProducts();

        writeCategoriesByProductCountDesc();

        writeUsersAndProducts();

    }

    private void writeUsersAndProducts() throws IOException {
        List<UserWithProductViewDTO> userWithProductViewDTOS = this.userService.getUsersThatHaveSoldProducts();

        UsersAndProductsViewDTO usersAndProductsViewDTO = new UsersAndProductsViewDTO();

        usersAndProductsViewDTO.setUsersCount(userWithProductViewDTOS.size());
        usersAndProductsViewDTO.setUsers(userWithProductViewDTOS);

        this.fileUtil.write(this.gson.toJson(usersAndProductsViewDTO), ConstantMsg.PATH_USERS_AND_PRODUCT);
    }

    private void writeCategoriesByProductCountDesc() throws IOException {
        List<CategoryByProductsViewDTO> categoryByProductsViewDTOS = this.categoryService.getCategoriesByProductCount();

        this.fileUtil.write(this.gson.toJson(categoryByProductsViewDTOS.toArray()), ConstantMsg.PATH_CATEGORIES_BY_PRODUCT);
    }

    private void writeUsersWithSoldProducts() throws IOException {
        List<UserSoldProductViewDTO> userSoldProductViewDTOS = this.userService.getUsersThatHaveSoldItems();

        this.fileUtil.write(this.gson.toJson(userSoldProductViewDTOS.toArray()), ConstantMsg.PATH_USERS_SOLD_PRODUCTS);
    }

    private void writeProductsWithoutSellerInRange() throws IOException {
        List<ProductWithSellerDTO> productWithSellerDTOS = this.productsService
                .getProductsByPriceBetween(new BigDecimal(500), new BigDecimal(1000));

        this.fileUtil.write(this.gson.toJson(productWithSellerDTOS.toArray()), ConstantMsg.PATH_PRODUCTS_IN_RANGE);
    }



}
