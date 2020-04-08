package com.productsshopxml.controller;

import com.productsshopxml.common.ConstantMsg;
import com.productsshopxml.services.categoryServices.CategoryService;
import com.productsshopxml.services.productServices.ProductsService;
import com.productsshopxml.services.userServices.UserService;
import com.productsshopxml.utils.xmlParser.XMLParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AppController implements CommandLineRunner {
    private UserService userService;
    private ProductsService productsService;
    private CategoryService categoryService;
    private XMLParser xmlParser;

    @Autowired
    public AppController(UserService userService, ProductsService productsService, CategoryService categoryService, XMLParser xmlParser) {
        this.userService = userService;
        this.productsService = productsService;
        this.categoryService = categoryService;
        this.xmlParser = xmlParser;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.productsService.seedProducts();

        //резултатите са в resources/results
        // 1.
        this.xmlParser.exportToXML(this.productsService
                        .getProductsByPriceBetween(new BigDecimal(500), new BigDecimal(1000))
                                                                        , ConstantMsg.PATH_PRODUCTS_IN_RANGE);


        // 2.  ?
        this.xmlParser.exportToXML(this.userService
                        .getUsersThatHaveSoldItems(), ConstantMsg.PATH_USERS_SOLD_PRODUCTS);

        // 3.
        this.xmlParser.exportToXML(this.categoryService
                        .getCategoriesByProductCount(), ConstantMsg.PATH_CATEGORIES_BY_PRODUCT);

        // 4.
        this.xmlParser.exportToXML(this.userService
                        .getUsersThatHaveSoldProducts(), ConstantMsg.PATH_USERS_AND_PRODUCT);


    }

}
