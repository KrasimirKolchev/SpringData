package com.cardealer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.cardealer.utils.fileUtil.FileUtil;
import com.cardealer.utils.fileUtil.FileUtilImpl;
import com.cardealer.utils.validationUtil.ValidationUtil;
import com.cardealer.utils.validationUtil.ValidationUtilImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeanConfig {
    
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
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
