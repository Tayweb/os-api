package com.spring.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;

public class ModelMapperConfig {

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }
}
