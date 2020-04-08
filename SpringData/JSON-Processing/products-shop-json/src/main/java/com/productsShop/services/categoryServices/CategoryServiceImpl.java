package com.productsShop.services.categoryServices;

import com.google.gson.Gson;
import com.productsShop.common.ConstantMsg;
import com.productsShop.persistence.models.dtos.categoryDtos.CategoryByProductsViewDTO;
import com.productsShop.persistence.models.dtos.categoryDtos.CategoryCreateDTO;
import com.productsShop.persistence.models.entities.Category;
import com.productsShop.persistence.models.entities.Product;
import com.productsShop.persistence.repositories.CategoriesRepository;
import com.productsShop.utils.fileUtil.FileUtil;
import com.productsShop.utils.validationUtil.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoriesRepository categoriesRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private Gson gson;
    private FileUtil fileUtil;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository, ValidationUtil validationUtil,
                               ModelMapper modelMapper, Gson gson, FileUtil fileUtil) {
        this.categoriesRepository = categoriesRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {
        if (this.categoriesRepository.count() == 0) {
            CategoryCreateDTO[] categoryCreateDTOS = this.gson
                    .fromJson(this.fileUtil
                            .readFileContent(ConstantMsg.CATEGORIES_FILE_PATH), CategoryCreateDTO[].class);


            Arrays.stream(categoryCreateDTOS).forEach(c -> {
                if (this.validationUtil.isValid(c)) {
                    this.categoriesRepository.saveAndFlush(this.modelMapper.map(c, Category.class));
                } else {
                    this.validationUtil.getViolationsData(c)
                            .stream().map(ConstraintViolation::getMessage)
                            .forEach(System.out::println);
                }
            });
        }
    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();
        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            long index = random.nextInt((int) this.categoriesRepository.count()) + 1;
            Category category = this.categoriesRepository.getOne(index);

            for (Category c: categories) {
                if (c.getId() == category.getId()) {
                    return categories;
                }
            }
            categories.add(category);
        }

        return categories;
    }

    @Override
    public List<CategoryByProductsViewDTO> getCategoriesByProductCount() {
        this.modelMapper.addMappings(new PropertyMap<Category, CategoryByProductsViewDTO>() {
            @Override
            protected void configure() {
                map().setCategory(source.getName());
            }
        });

        return this.categoriesRepository.getCategoriesByProductsOrderByProductsCount()
                .stream().map(c -> {
                    CategoryByProductsViewDTO cat = this.modelMapper.map(c, CategoryByProductsViewDTO.class);
                    cat.setProductsCount(c.getProducts().size());
                    BigDecimal totalPrice = BigDecimal.ZERO;

                    for (Product p: c.getProducts()) {
                        totalPrice = totalPrice.add(p.getPrice());
                    }

                    cat.setTotalRevenue(totalPrice);
                    //MathContext added as the result of division must be unlimited
                    BigDecimal avPrice = totalPrice.divide(new BigDecimal(c.getProducts().size()), MathContext.DECIMAL64);
                    cat.setAveragePrice(avPrice);

                    return cat;
                }).collect(Collectors.toList());
    }
}
