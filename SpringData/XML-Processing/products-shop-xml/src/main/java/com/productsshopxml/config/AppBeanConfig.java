package com.productsshopxml.config;


import com.productsshopxml.utils.fileUtil.FileUtil;
import com.productsshopxml.utils.fileUtil.FileUtilImpl;
import com.productsshopxml.utils.validationUtil.ValidationUtil;
import com.productsshopxml.utils.validationUtil.ValidationUtilImpl;
import com.productsshopxml.utils.xmlParser.XMLParser;
import com.productsshopxml.utils.xmlParser.XMLParserImpl;
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
    public ValidationUtil validationUtil(){
        return new ValidationUtilImpl();
    }
    
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XMLParser xmlParser() {
        return new XMLParserImpl();
    }

}
