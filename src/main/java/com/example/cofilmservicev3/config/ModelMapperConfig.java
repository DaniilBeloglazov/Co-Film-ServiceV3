package com.example.cofilmservicev3.config;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setSkipNullEnabled(true);

        modelMapper.getConfiguration()
                .setPropertyCondition((mappingContext) -> {
                    if (mappingContext.getSource() instanceof String) {
                        return null != mappingContext.getSource() && StringUtils.isNotBlank((String) mappingContext.getSource());
                    } else {
                        return mappingContext.getSource() != null;
                    }
                });

        return modelMapper;
    }
}
