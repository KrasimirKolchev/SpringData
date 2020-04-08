package com.productsshopxml.services.categoryServices;

import com.productsshopxml.common.ConstantMsg;
import com.productsshopxml.persistence.models.dtos.categoryDtos.CategoriesByProductRootDTO;
import com.productsshopxml.persistence.models.dtos.categoryDtos.CategoryByProductsViewDTO;
import com.productsshopxml.persistence.models.dtos.categoryDtos.CategoryRootDTO;
import com.productsshopxml.persistence.models.entities.Category;
import com.productsshopxml.persistence.models.entities.Product;
import com.productsshopxml.persistence.repositories.CategoriesRepository;
import com.productsshopxml.utils.validationUtil.ValidationUtil;
import com.productsshopxml.utils.xmlParser.XMLParser;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoriesRepository categoriesRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private XMLParser xmlParser;

    public CategoryServiceImpl(CategoriesRepository categoriesRepository, ValidationUtil validationUtil,
                               ModelMapper modelMapper, XMLParser xmlParser) {
        this.categoriesRepository = categoriesRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCategories() throws IOException, JAXBException {

        if (this.categoriesRepository.count() == 0) {
            CategoryRootDTO categories = this.xmlParser.importFromXML(CategoryRootDTO.class,
                                                                            ConstantMsg.CATEGORIES_FILE_PATH);

            categories.getCategories().forEach(c -> {
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
    public CategoriesByProductRootDTO getCategoriesByProductCount() {
        this.modelMapper.addMappings(new PropertyMap<Category, CategoryByProductsViewDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
            }
        });

        CategoriesByProductRootDTO categories = new CategoriesByProductRootDTO();
        categories.setCategories(this.categoriesRepository.getCategoriesByProductsOrderByProductsCount()
                .stream().map(c -> {
                    CategoryByProductsViewDTO cat = this.modelMapper.map(c, CategoryByProductsViewDTO.class);
                    cat.setProductsCount(c.getProducts().size());
                    BigDecimal totalPrice = BigDecimal.ZERO;

                    for (Product p: c.getProducts()) {
                        totalPrice = totalPrice.add(p.getPrice());
                    }

                    cat.setTotalRevenue(totalPrice);
                    //MathContext added as the result of division must be unlimited, maybe?!?
                    BigDecimal avPrice = totalPrice.divide(new BigDecimal(c.getProducts().size()), MathContext.DECIMAL32);
                    cat.setAveragePrice(avPrice);

                    return cat;
                }).collect(Collectors.toList()));

        return categories;
    }
}
