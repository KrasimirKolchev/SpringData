package com.productsshopxml.services.userServices;

import com.productsshopxml.persistence.models.dtos.userDtos.UserAndProductsRootDTO;
import com.productsshopxml.persistence.models.dtos.userDtos.UsersWithSoldProductsRootDTO;
import com.productsshopxml.persistence.models.entities.User;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public interface UserService {
    void seedUsers() throws IOException, JAXBException;

    void register(User user);

    User getRandomUser();

    UsersWithSoldProductsRootDTO getUsersThatHaveSoldItems();

    UserAndProductsRootDTO getUsersThatHaveSoldProducts();

}
