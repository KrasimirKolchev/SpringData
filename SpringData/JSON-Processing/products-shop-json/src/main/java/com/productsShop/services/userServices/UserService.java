package com.productsShop.services.userServices;

import com.productsShop.persistence.models.dtos.productDtos.UserSoldProductViewDTO;
import com.productsShop.persistence.models.dtos.userDtos.UserWithProductViewDTO;
import com.productsShop.persistence.models.entities.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface UserService {
    void seedUsers() throws IOException;

    void register(User user);

    User getRandomUser();

    List<UserSoldProductViewDTO> getUsersThatHaveSoldItems();

    List<UserWithProductViewDTO> getUsersThatHaveSoldProducts();

}
