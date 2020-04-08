package com.productsShop.services.categoryServices;

import com.productsShop.persistence.models.dtos.categoryDtos.CategoryByProductsViewDTO;
import com.productsShop.persistence.models.entities.Category;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public interface CategoryService {
    void seedCategories() throws IOException;

    Set<Category> getRandomCategories();

    List<CategoryByProductsViewDTO> getCategoriesByProductCount();
}
