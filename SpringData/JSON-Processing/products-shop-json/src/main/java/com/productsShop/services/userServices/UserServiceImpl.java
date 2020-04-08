package com.productsShop.services.userServices;

import com.google.gson.Gson;
import com.productsShop.common.ConstantMsg;
import com.productsShop.persistence.models.dtos.userDtos.ProductNamePriceViewDTO;
import com.productsShop.persistence.models.dtos.userDtos.SoldProductsViewDTO;
import com.productsShop.persistence.models.dtos.userDtos.UserCreateDTO;
import com.productsShop.persistence.models.dtos.productDtos.UserSoldProductViewDTO;
import com.productsShop.persistence.models.dtos.userDtos.UserWithProductViewDTO;
import com.productsShop.persistence.models.entities.User;
import com.productsShop.persistence.repositories.UserRepository;
import com.productsShop.utils.fileUtil.FileUtil;
import com.productsShop.utils.validationUtil.ValidationUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private Gson gson;
    private FileUtil fileUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ValidationUtil validationUtil,
                           ModelMapper modelMapper, Gson gson, FileUtil fileUtil) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public void register(User user) {
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void seedUsers() throws IOException {
        if (this.userRepository.count() == 0) {
            UserCreateDTO[] userCreateDTOS = this.gson
                    .fromJson(this.fileUtil
                            .readFileContent(ConstantMsg.USERS_FILE_PATH), UserCreateDTO[].class);

            Arrays.stream(userCreateDTOS).forEach(u -> {
                if (this.validationUtil.isValid(u)) {
                    this.register(this.modelMapper.map(u, User.class));
                } else {
                    this.validationUtil.getViolationsData(u)
                            .stream().map(ConstraintViolation::getMessage)
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
    public List<UserSoldProductViewDTO> getUsersThatHaveSoldItems() {

        List<User> users = this.userRepository.getUsersThatHaveSoldItems()
                .stream().map(s -> {
                    s.setSoldProducts(s.getSoldProducts()
                            .stream().filter(p -> p.getBuyer() != null)
                            .collect(Collectors.toList()));
                    return s;
                }).collect(Collectors.toList());

        return users.stream().map(u -> this.modelMapper.map(u, UserSoldProductViewDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserWithProductViewDTO> getUsersThatHaveSoldProducts() {

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

        List<User> users = this.userRepository.getUsersWithSoldProductsOrderByProductsDesc();

        return users.stream()
                .map(u -> this.modelMapper.map(u, UserWithProductViewDTO.class))
                .collect(Collectors.toList());

    }
}
