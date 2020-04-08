package com.bookshopsystem.services.implementations;

import com.bookshopsystem.models.Category;
import com.bookshopsystem.repositories.CategoryRepository;
import com.bookshopsystem.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void register(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public long categoriesCount() {
        return this.categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategories() {
        long count = this.categoryRepository.count();
        if (count < 1L) {
            throw new RuntimeException("No categories in database");
        }

        List<Category> categories = this.categoryRepository.findAll();
        Random random = new Random();
        Set<Category> result = new HashSet<>();

        while (random.nextInt(2) > 0) {
            int index = (int) ((random.nextLong() & Long.MAX_VALUE) % count);
            result.add(categories.get(index));
        }

        return result;
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }
}
