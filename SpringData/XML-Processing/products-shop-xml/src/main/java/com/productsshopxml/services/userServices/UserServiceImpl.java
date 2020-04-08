package com.productsshopxml.services.userServices;


import com.productsshopxml.common.ConstantMsg;
import com.productsshopxml.persistence.models.dtos.userDtos.*;
import com.productsshopxml.persistence.models.dtos.productDtos.ProductNamePriceViewDTO;
import com.productsshopxml.persistence.models.dtos.productDtos.SoldProductsViewDTO;
import com.productsshopxml.persistence.models.entities.User;
import com.productsshopxml.persistence.repositories.UserRepository;
import com.productsshopxml.utils.validationUtil.ValidationUtil;
import com.productsshopxml.utils.xmlParser.XMLParser;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private XMLParser xmlParser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidationUtil validationUtil,
                           ModelMapper modelMapper, XMLParser xmlParser) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;

    }

    @Override
    public void register(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void seedUsers() throws JAXBException, FileNotFoundException {
        if (this.userRepository.count() == 0) {
            UserRootDTO userRootDTO = this.xmlParser.importFromXML(UserRootDTO.class, ConstantMsg.USERS_FILE_PATH);

            userRootDTO.getUsers().forEach(u -> {
                if (this.validationUtil.isValid(u)) {
                    this.userRepository.saveAndFlush(this.modelMapper.map(u, User.class));
                } else {
                    this.validationUtil.getViolationsData(u).stream()
                            .map(ConstraintViolation::getMessage)
                            .forEach(System.out::println);
                }
            });
        }
    }

    @Override
    public User getRandomUser() {
        Random random = new Random();
        long index = random.nextInt((int) this.userRepository.count()) + 1;

        return this.userRepository.getOne(index);
    }

    @Override
    public UsersWithSoldProductsRootDTO getUsersThatHaveSoldItems() {

        List<User> users = this.userRepository.getUsersThatHaveSoldItems()
                .stream().map(s -> {
                    s.setSoldProducts(s.getSoldProducts()
                            .stream().filter(p -> p.getBuyer() != null)
                            .collect(Collectors.toList()));
                    return s;
                }).collect(Collectors.toList());

        UsersWithSoldProductsRootDTO user = new UsersWithSoldProductsRootDTO();

        user.setUsers(users.stream().map(u -> this.modelMapper.map(u, UserSoldProductViewDTO.class))
                .collect(Collectors.toList()));

        return user;
    }

    @Override
    public UserAndProductsRootDTO getUsersThatHaveSoldProducts() {

        Converter<User, UserWithProductViewDTO> userWithProductConverter = new Converter<User, UserWithProductViewDTO>() {
            @Override
            public UserWithProductViewDTO convert(MappingContext<User, UserWithProductViewDTO> context) {

                List<ProductNamePriceViewDTO> products = context
                        .getSource()
                        .getSoldProducts()
                        .stream()
                        .map(product -> modelMapper.map(product, ProductNamePriceViewDTO.class))
                        .sorted((p1, p2) -> p2.getPrice().subtract(p1.getPrice()).intValue())
                        .collect(Collectors.toList());

                SoldProductsViewDTO soldProducts = new SoldProductsViewDTO();
                soldProducts.setCount(products.size());
                soldProducts.setProducts(products);

                UserWithProductViewDTO up = new UserWithProductViewDTO();
                up.setSoldProducts(soldProducts);
                up.setAge(context.getSource().getAge());
                up.setFirstName(context.getSource().getFirstName());
                up.setLastName(context.getSource().getLastName());

                return up;
            }
        };

        this.modelMapper.createTypeMap(User.class, UserWithProductViewDTO.class)
                .setConverter(userWithProductConverter);

        UserAndProductsRootDTO usersRoot = new UserAndProductsRootDTO();
        usersRoot.setUsers(this.userRepository.getUsersWithSoldProductsOrderByProductsDesc()
                .stream()
                .map(u -> this.modelMapper.map(u, UserWithProductViewDTO.class))
                .collect(Collectors.toList()));
        usersRoot.setCount(usersRoot.getUsers().size());

        return usersRoot;

    }
}
