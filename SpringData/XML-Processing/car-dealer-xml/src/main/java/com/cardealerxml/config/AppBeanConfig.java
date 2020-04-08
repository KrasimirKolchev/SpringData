package com.cardealerxml.config;

import com.cardealerxml.utils.fileUtil.FileUtil;
import com.cardealerxml.utils.fileUtil.FileUtilImpl;
import com.cardealerxml.utils.validationUtil.ValidationUtil;
import com.cardealerxml.utils.validationUtil.ValidationUtilImpl;
import com.cardealerxml.utils.xmlParser.XMLParser;
import com.cardealerxml.utils.xmlParser.XMLParserImpl;
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
