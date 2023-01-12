package com.example.cofilmservicev3.config;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

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
                })
                .setPropertyCondition(mappingContext -> {
                    if (mappingContext.getSource() instanceof List<?>) {
                        return null != mappingContext.getSource() && !((List<?>)mappingContext.getSource()).isEmpty();
                    } else {
                        return mappingContext.getSource() != null;
                    }
                });

        return modelMapper;
    }
}
