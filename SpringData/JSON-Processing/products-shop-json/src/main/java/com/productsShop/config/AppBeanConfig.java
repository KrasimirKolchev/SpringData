package com.productsShop.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.productsShop.utils.fileUtil.FileUtil;
import com.productsShop.utils.fileUtil.FileUtilImpl;
import com.productsShop.utils.validationUtil.ValidationUtil;
import com.productsShop.utils.validationUtil.ValidationUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeanConfig {
//    private static ModelMapper modelMapper;
    
    @Bean
    public FileUtil fileUtil() {
        return new FileUtilImpl();
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Bean
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }

//    static {
//        modelMapper = new ModelMapper();
//
//        modelMapper.addMappings(new PropertyMap<UsersAndProductsViewDTO, SoldProductsViewDTO>() {
//            @Override
//            protected void configure() {
//                map().setCount(source.getUsers().size());
//            }
//        });
//    }
    
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
