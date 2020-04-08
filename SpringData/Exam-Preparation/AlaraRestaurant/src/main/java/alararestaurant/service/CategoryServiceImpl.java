package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        List<Category> categories = this.categoryRepository.findAll();

        StringBuilder sb = new StringBuilder();

        categories.stream()
                .sorted((a, b) -> {
                    int sort = Integer.compare(b.getItems().size(), a.getItems().size());
                    if (sort == 0) {
                        BigDecimal val1 = a.getItems().stream().map(Item::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                        BigDecimal val2 = b.getItems().stream().map(Item::getPrice).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                        sort = val2.compareTo(val1);
                    }
                    return sort;
                }).forEach(c -> {
                    sb.append(String.format("\nCategory: %s", c.getName()));
                    c.getItems().forEach(i -> {
                        sb.append(String.format("\n--- Item Name: %s\n--- Item Price: %s\n", i.getName(), i.getPrice()));
                    });
        });


        return sb.toString();
    }

    @Override
    public void register(Category category) {
        this.categoryRepository.saveAndFlush(category);
    }

    @Override
    public Category findCategoryByName(String name) {
        return this.categoryRepository.findCategoryByName(name);
    }
}
