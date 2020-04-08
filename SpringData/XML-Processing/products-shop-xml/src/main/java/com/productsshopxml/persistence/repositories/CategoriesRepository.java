package com.productsshopxml.persistence.repositories;

import com.productsshopxml.persistence.models.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {

    @Query("select c from com.productsshopxml.persistence.models.entities.Category c order by c.products.size desc ")
    List<Category> getCategoriesByProductsOrderByProductsCount();
}
