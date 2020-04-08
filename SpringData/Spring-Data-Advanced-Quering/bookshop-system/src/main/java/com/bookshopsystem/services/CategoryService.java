package com.bookshopsystem.services;

import com.bookshopsystem.models.Category;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface CategoryService {
    void register(Category category);

    long categoriesCount();

    Set<Category> getRandomCategories();

    Category getCategoryById(Long id);
}
