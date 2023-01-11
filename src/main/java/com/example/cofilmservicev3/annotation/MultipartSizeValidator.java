package com.example.cofilmservicev3.annotation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipartSizeValidator implements ConstraintValidator<MultipartSize, MultipartFile> {
    private long min;
    private long max;
    @Override
    public void initialize(MultipartSize constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        if (value == null)
            return true;
        long multipartSize = value.getSize();
        return multipartSize >= min && multipartSize <= max;
    }
}
