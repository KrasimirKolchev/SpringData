package alararestaurant.service;

import alararestaurant.domain.entities.Category;

public interface CategoryService {

    String exportCategoriesByCountOfItems();

    void register(Category category);

    Category findCategoryByName(String name);
}
