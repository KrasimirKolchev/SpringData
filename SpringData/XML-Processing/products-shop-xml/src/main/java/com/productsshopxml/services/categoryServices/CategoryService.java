package com.productsshopxml.services.categoryServices;


import com.productsshopxml.persistence.models.dtos.categoryDtos.CategoriesByProductRootDTO;
import com.productsshopxml.persistence.models.entities.Category;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Set;

@Service
public interface CategoryService {
    void seedCategories() throws IOException, JAXBException;

    Set<Category> getRandomCategories();

    CategoriesByProductRootDTO getCategoriesByProductCount();
}
