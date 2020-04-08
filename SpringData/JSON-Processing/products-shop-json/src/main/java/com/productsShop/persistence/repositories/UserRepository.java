package com.productsShop.persistence.repositories;

import com.productsShop.persistence.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from com.productsShop.persistence.models.entities.User u " +
            "join com.productsShop.persistence.models.entities.Product p on u.id = p.seller.id " +
            "where p.buyer.id is not null order by u.lastName, u.firstName")
    List<User> getUsersThatHaveSoldItems();


    @Query("select distinct u from com.productsShop.persistence.models.entities.User u " +
            "join com.productsShop.persistence.models.entities.Product p on u.id = p.seller.id " +
            "order by u.soldProducts.size desc, u.lastName asc ")
    List<User> getUsersWithSoldProductsOrderByProductsDesc();
}
